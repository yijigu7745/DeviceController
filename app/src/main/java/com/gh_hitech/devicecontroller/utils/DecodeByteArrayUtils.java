package com.gh_hitech.devicecontroller.utils;

import android.content.Intent;

/**
 * @author yijigu
 */
public class DecodeByteArrayUtils {
    /**
     * 十六进制0-59所对应的的十进制值
     * 【数组下标可认为是十六进制值，所对应的数组值便是该下标值在十进制下的值】
     * 例如十六进制10在数组里为第11位，下标为10，所对应的的数组值在十进制下为16。
     */
    private static final byte[] timeByteContent = new byte[]{0,1,2,3,4,5,6,7,8,9,16,17,18,19,20,21,22,23,24,25,32,33,34,35,36,37,38,39,40,41,48,49,50,51,52,53,54,55,56,57,64,65,66,67,68,69,70,71,72,73,80,81,82,83,84,85,86,87,88,89};
    /**
     * 星期
     * 【数组下标1代表星期日，以此类推】
     */
    private static final String[] weekByContent = {"星期","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    /**
     * 日期格式长度
     * 秒、分、时、星期、日、月、年
     */
    private static final int responseByteLength = 7;

    /**
     * 将服务器返回的byte数组解析获得设备时间信息
     * @param bytes
     * @return
     */
    public static String decodeTime(byte[] bytes){
        StringBuilder time = new StringBuilder();
        for(int j = bytes.length -1; j >= 0;j--) {
            for(int i = 1; i< timeByteContent.length ;i++) {
                if(timeByteContent[i] == bytes[j]) {
                    time.append(i).append(" ");
                }
            }
        }
        String[] timeContent = time.toString().split(" ");
        StringBuffer timeBuffer = new StringBuffer();
        if(timeContent.length != responseByteLength){
            timeBuffer.append("解析错误");
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            String year = stringBuilder.append(20).append(timeContent[0]).append("年").toString();
            stringBuilder.setLength(0);
            String month = stringBuilder.append(timeContent[1]).append("月").toString();
            stringBuilder.setLength(0);
            String date = stringBuilder.append(timeContent[2]).append("日").toString();
            stringBuilder.setLength(0);
            String week = weekByContent[Integer.parseInt(timeContent[3])];
            stringBuilder.setLength(0);
            String hour = stringBuilder.append(timeContent[4]).append("时").toString();
            stringBuilder.setLength(0);
            String minute = stringBuilder.append(timeContent[5]).append("分").toString();
            stringBuilder.setLength(0);
            String second = stringBuilder.append(timeContent[6]).append("秒").toString();
            timeBuffer.append(year).append(month).append(date).append(week).append(hour).append(minute).append(second);
        }
        return timeBuffer.toString();
    }

    /**
     * 将传入的时间按格式变成设备可识别的字符串并传给设备
     * @param time 传入时间格式：yy-MM-dd-hh-mm-ss-week
     * @return
     */
    public static String encodeTime(String time){
        String[] timeArray = time.split("-");
        StringBuilder timeContent = new StringBuilder();
        if(timeArray.length != responseByteLength){
            timeContent.append("解析错误");
            return null;
        }else {
            byte[] timeByte = new byte[7];
            int week = Integer.parseInt(timeArray[6]);
            int second = timeByteContent[Integer.parseInt(timeArray[5])];
            int minute = timeByteContent[Integer.parseInt(timeArray[4])];
            int hour = timeByteContent[Integer.parseInt(timeArray[3])];
            int date = timeByteContent[Integer.parseInt(timeArray[2])];
            int month = timeByteContent[Integer.parseInt(timeArray[1])];
            int year = timeByteContent[Integer.parseInt(timeArray[0])-2000];
            timeByte[0] = (byte) second;
            timeByte[1] = (byte) minute;
            timeByte[2] = (byte) hour;
            timeByte[3] = (byte) week;
            timeByte[4] = (byte) date;
            timeByte[5] = (byte) month;
            timeByte[6] = (byte) year;
            timeContent.append(new String(timeByte));
        }
        return timeContent.toString();
    }
}
