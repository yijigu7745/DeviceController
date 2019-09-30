package com.gh_hitech.devicecontroller.retrofit.factory;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    MyGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        JsonReader jsonReader;
        Log.i("MyGsonConverter", "ResponseBody:\n" + response);
        byte[] valueBytes = response.getBytes();
        try {
            InputStreamReader charReader = new InputStreamReader(new ByteArrayInputStream(valueBytes), "UTF-8");
            jsonReader = gson.newJsonReader(charReader);
            int missionType = -1;
            /**
             * 将解析的"data"里的所有数据重新组装成json格式的字符串，用于向下转型成不同类型的Bean。
             */
            StringBuilder data = new StringBuilder();
            /**
             * 服务器返回json中，除去"data"字段数据以外的所有字段，用于组装ResponseBean。
             */
            StringBuilder responseBean = new StringBuilder();
            /**
             * 解析完成标志 用于解析整个json数据。
             */
            boolean decodeJson = true;
            /**
             * 解析完成标志 用于解析json中字段名为"data"的数据。
             */
            boolean decodeData = true;
            boolean isArrayOrObject = false;
            //解析固定返回类型的json逻辑，使用循环将json整个解析完全
            while (decodeJson) {
                switch (jsonReader.peek()) {
                    case NAME:
                        String nextName = jsonReader.nextName();
                        responseBean.append(",").append("\"").append(nextName).append("\"").append(":");
                        if ("type".equals(nextName)) {
                            missionType = jsonReader.nextInt();
                            responseBean.append(missionType);
                        }
                        //data里的数据可能为json也可能为空字符串""
                        if ("data".equals(nextName)) {
                            responseBean.append("{}");
                            while (decodeData) {
                                switch (jsonReader.peek()) {
                                    case NAME:
                                        data.append(",").append("\"").append(jsonReader.nextName()).append("\"").append(":");
                                        break;
                                    case NUMBER:
                                        data.append(jsonReader.nextInt());
                                        break;
                                    case STRING:
                                        data.append("\"").append(jsonReader.nextString()).append("\"");
                                        break;
                                    case BEGIN_OBJECT:
                                        data.append("{");
                                        jsonReader.beginObject();
                                        isArrayOrObject = true;
                                        break;
                                    case END_OBJECT:
                                        decodeData = false;
                                        data.deleteCharAt(1).append("}");
                                        jsonReader.endObject();
                                        isArrayOrObject = true;
                                        break;
                                    case BEGIN_ARRAY:
                                        data.append("[");
                                        jsonReader.beginArray();
                                        isArrayOrObject = true;
                                        break;
                                    case END_ARRAY:
                                        data.append("]");
                                        jsonReader.endArray();
                                        isArrayOrObject = true;
                                        break;
                                    case NULL:
                                        data.append("\"\"");
                                        jsonReader.nextNull();
                                        if (!isArrayOrObject) {
                                            decodeData = false;
                                        }
                                        break;
                                    case BOOLEAN:
                                        data.append(jsonReader.nextBoolean());
                                        break;
                                }
                            }
                        }
                        break;
                    case NUMBER:
                        responseBean.append(jsonReader.nextInt());
                        break;
                    case STRING:
                        responseBean.append("\"").append(jsonReader.nextString()).append("\"");
                        break;
                    case BEGIN_OBJECT:
                        responseBean.append("{");
                        jsonReader.beginObject();
                        break;
                    case END_OBJECT:
                        responseBean.deleteCharAt(1).append("}");
                        jsonReader.endObject();
                        decodeJson = false;
                        break;
                    case END_ARRAY:
                        responseBean.append("]");
                        jsonReader.endArray();
                        break;
                    case NULL:
                        responseBean.append("\"\"");
                        jsonReader.nextNull();
                        break;
                    case BOOLEAN:
                        responseBean.append(jsonReader.nextBoolean());
                        break;
                    case BEGIN_ARRAY:
                        responseBean.append("[");
                        jsonReader.beginArray();
                        break;
                }
            }
            Log.i("MyGsonConverter", "responseBean :" + responseBean.toString());
            Log.i("MyGsonConverter", "data :" + data.toString());
            //根据获取到的任务类别，在responseBean中返回不同类型的Bean
//            switch (missionType) {
//                case MissionTypeConstants.CHANGE_ACCOUNT:
//                    ResponseBean<ChangeAccountBean> responseChangeAccountBean = gson.fromJson(responseBean.toString(), ResponseBean.class);
//                    ChangeAccountBean changeAccountBean = gson.fromJson(data.toString(), ChangeAccountBean.class);
//                    responseChangeAccountBean.setData(changeAccountBean);
//                    return (T) responseChangeAccountBean;
//                case MissionTypeConstants.ACCOUNT_MISSION:
//                    ResponseBean<AccountUploadMissionBean> responseAccountMissionBean = gson.fromJson(responseBean.toString(), ResponseBean.class);
//                    AccountUploadMissionBean accountUploadMissionBean = gson.fromJson(data.toString(), AccountUploadMissionBean.class);
//                    responseAccountMissionBean.setData(accountUploadMissionBean);
//                    return (T) responseAccountMissionBean;
//                default:
//            }

            return gson.fromJson(response, type);
        } finally {
            value.close();
        }
    }
}
