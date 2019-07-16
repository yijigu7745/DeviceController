package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.model.Command;
import com.gh_hitech.devicecontroller.model.CommandBean;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.DeviceCommandPresenter;
import com.gh_hitech.devicecontroller.ui.ImageAndTextView;
import com.gh_hitech.devicecontroller.ui.SwitchButton;
import com.gh_hitech.devicecontroller.utils.ArrayUtils;
import com.gh_hitech.devicecontroller.utils.Constants;
import com.gh_hitech.devicecontroller.utils.DecodeByteArrayUtils;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 控制面板
 * @author yijigu
 */
public class ControlActivity extends BaseActivity implements IView, SwitchButton.OnCheckedChangeListener,
        View.OnClickListener {

    private final static String TAG = "ControlActivity";
    private final static int LINE_1 = 1;
    private final static int LINE_2 = 2;
    private final static int LINE_3 = 3;
    private final static int LINE_4 = 4;
    private final static int LINE_5 = 5;
    private final static int LINE_6 = 6;
    private final static int LINE_7 = 7;
    private final static int LINE_8 = 8;
    private final static int SWITCH_UNCHECKED = 0;
    private final static int SWITCH_CHECKED = 1;
    SweetDialog sweetDialog;
    @BindView(R.id.btn_get_time)
    Button btnGetTime;
    @BindView(R.id.btn_delay_switch)
    Button btnDelaySwitch;
    @BindView(R.id.btn_set_time)
    Button btnSetTime;
    @BindView(R.id.switch1)
    SwitchButton switch1;
    @BindView(R.id.switch2)
    SwitchButton switch2;
    @BindView(R.id.switch3)
    SwitchButton switch3;
    @BindView(R.id.switch4)
    SwitchButton switch4;
    @BindView(R.id.switch5)
    SwitchButton switch5;
    @BindView(R.id.switch6)
    SwitchButton switch6;
    @BindView(R.id.switch7)
    SwitchButton switch7;
    @BindView(R.id.switch8)
    SwitchButton switch8;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_delay_time)
    EditText delayTime;
    @BindView(R.id.et_enter_time)
    EditText enterTime;
    @BindView(R.id.spinner)
    Spinner lineSpinner;
    @BindView(R.id.tv1)
    TextView tvName;
    /**
     * 本地做修改的开关状态
     */
    private int[] lineStatusLocal = new int[8];
    /**
     * 服务器获取的开关状态
     */
    private int[] lineStatusDevice = new int[8];
    /**
     * 控制器开关描述
     */
    private String[] lineDesc;
    private DeviceBean deviceBean;
    private int selectLine = -1;

    private DeviceCommandPresenter commandPresenter;


    private TextView tvTitle;
    private RelativeLayout layoutRight;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        getLayoutInflater().inflate(R.layout.toobar_layout, toolbar);

        //设置回退按钮
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        // toolbar返回事件
        toolbar.setNavigationOnClickListener(view -> ControlActivity.this.finish());

        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("控制面板");

        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        ButterKnife.bind(this);
        sweetDialog = SweetDialog.builder(this);
        init();
        register();
        getDeviceLineStatus();
    }

    private void init() {
        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra("deviceBean");
        deviceBean = ((DeviceBean) serializable);
        tvName.setText(deviceBean.getName()+getString(R.string.linestatus));
        commandPresenter = new DeviceCommandPresenter(this);
        final String[] spinnerItems = {"请选择","1","2","3","4","5","6","7","8"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,spinnerItems);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lineSpinner.setAdapter(spinnerAdapter);
    }

    private void register() {
        btnDelaySwitch.setOnClickListener(this);
        btnGetTime.setOnClickListener(this);
        btnSetTime.setOnClickListener(this);
        switch1.setOnCheckedChangeListener(this);
        switch2.setOnCheckedChangeListener(this);
        switch3.setOnCheckedChangeListener(this);
        switch4.setOnCheckedChangeListener(this);
        switch5.setOnCheckedChangeListener(this);
        switch6.setOnCheckedChangeListener(this);
        switch7.setOnCheckedChangeListener(this);
        switch8.setOnCheckedChangeListener(this);
        lineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectLine = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDeviceLineStatus() {
        if (deviceBean != null) {
            CommandBean commandBean = new CommandBean();
            commandBean.setCommand(Command.READ.getCommand());
            commandBean.setDeviceName(deviceBean.getName());
            commandPresenter.getDeviceLineStatus(commandBean)
                    .subscribe(resultModel -> {
                        String result = ((ResultModel<String>) resultModel).getData();
                        Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                        sweetDialog.success("加载成功").show();
                        initSwitchStatus(result.trim());
                    }, error -> {
                        Log.e(TAG, "getDeviceLineStatus: " + error);
                        sweetDialog.error("加载失败或设备不在线!").show();
                    });
        }
        if(Constants.TEST_MODE){
            lineDesc = new String[]{
                    "开关1",
                    "开关2",
                    "开关3",
                    "开关4",
                    "开关5",
                    "开关6",
                    "开关7",
                    "开关8"
            };
        }
    }

    private void initSwitchStatus(String content) {
        content = content.replace("relay", "");
        char[] oldResult = content.toCharArray();
        char[] newResult = new char[oldResult.length];
        ArrayUtils.copyCharArrayByDesc(oldResult,newResult);
        for (int i = 0; i < newResult.length; i++) {
            switch (newResult[i]) {
                case '0':
                    lineStatusDevice[i] = SWITCH_UNCHECKED;
                    break;
                case '1':
                    lineStatusDevice[i] = SWITCH_CHECKED;
                    break;
                default:
                    lineStatusDevice[i] = SWITCH_CHECKED;
            }
        }
        switch1.setChecked(lineStatusDevice[0] == SWITCH_CHECKED);
        switch2.setChecked(lineStatusDevice[1] == SWITCH_CHECKED);
        switch3.setChecked(lineStatusDevice[2] == SWITCH_CHECKED);
        switch4.setChecked(lineStatusDevice[3] == SWITCH_CHECKED);
        switch5.setChecked(lineStatusDevice[4] == SWITCH_CHECKED);
        switch6.setChecked(lineStatusDevice[5] == SWITCH_CHECKED);
        switch7.setChecked(lineStatusDevice[6] == SWITCH_CHECKED);
        switch8.setChecked(lineStatusDevice[7] == SWITCH_CHECKED);
        switch1.setButtonDesc(lineDesc[0]);
        switch2.setButtonDesc(lineDesc[1]);
        switch3.setButtonDesc(lineDesc[2]);
        switch4.setButtonDesc(lineDesc[3]);
        switch5.setButtonDesc(lineDesc[4]);
        switch6.setButtonDesc(lineDesc[5]);
        switch7.setButtonDesc(lineDesc[6]);
        switch8.setButtonDesc(lineDesc[7]);
        ArrayUtils.copyIntArray(lineStatusDevice, lineStatusLocal);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_time:
                btnGetTime.setClickable(false);
                getTime();
                break;
            case R.id.btn_delay_switch:
                btnDelaySwitch.setClickable(false);
                delaySwitch();
                break;
            case R.id.btn_set_time:
                btnSetTime.setClickable(false);
                setTime();
                break;
            default:
        }
    }

    private void setTime() {
        if(StringUtils.isBlank(enterTime.getText().toString())){
            sweetDialog.waring("请输入时间！",false).show();
        }
        CommandBean commandBean = new CommandBean();
        StringBuilder commandContent = new StringBuilder();
        String time = DecodeByteArrayUtils.encodeTime(enterTime.getText().toString());
        if(StringUtils.isBlank(time)){
            sweetDialog.waring("解析失败！请重新输入！",false).show();
        }
        commandBean.setCommand(commandContent.append(Command.SETTIME.getCommand()+time).toString());
        commandBean.setDeviceName(deviceBean.getName());
        commandPresenter.setDeviceTime(commandBean)
                .subscribe(resultModel -> {
                    String result = ((ResultModel<String>) resultModel).getData();
                    Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                    sweetDialog.success("设置成功！").show();
                    btnSetTime.setClickable(true);
                }, error -> {
                    Log.e(TAG, "getDeviceLineStatus: " + error);
                    sweetDialog.error("加载失败!").show();
                    btnSetTime.setClickable(true);
                });
    }

    private void getTime() {
        CommandBean commandBean = new CommandBean();
        StringBuilder commandContent = new StringBuilder();
        commandBean.setCommand(commandContent.append(Command.READTIME.getCommand()).toString());
        commandBean.setDeviceName(deviceBean.getName());
        commandPresenter.getDeviceTime(commandBean)
                .subscribe(resultModel -> {
                    String result = ((ResultModel<String>) resultModel).getData();
                    Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                    sweetDialog.success("加载成功").show();
                    refreshTime(result);
                }, error -> {
                    Log.e(TAG, "getDeviceLineStatus: " + error);
                    sweetDialog.error("加载失败!").show();
                    btnGetTime.setClickable(true);
                });
    }

    private void refreshTime(String content) {
        content = content.replace("rt:","");
        String time = DecodeByteArrayUtils.decodeTime(content.getBytes());
        tvTime.setText(time);
        btnGetTime.setClickable(true);
    }

    private void delaySwitch() {
        CommandBean commandBean = new CommandBean();
        StringBuilder commandContent = new StringBuilder();
        if(StringUtils.isBlank(delayTime.getText().toString())){
            sweetDialog.waring("请输入延时时间！",false).show();
            return;
        }
        int timeLength = 2;
        if(delayTime.getText().toString().length() != timeLength){
            sweetDialog.waring("输入长度错误！请输入两位数的时间！",false).show();
            return;
        }
        if(selectLine <= 0){
            sweetDialog.waring("请选择控制线路！",false).show();
            return;
        }
        commandContent.append(Command.DELAY.getCommand()).append(selectLine).append(":").append(delayTime.getText())
                .toString();
        commandBean.setCommand(commandContent.toString());
        commandBean.setDeviceName(deviceBean.getName());
        commandPresenter.delayTurnLine(commandBean)
                .subscribe(resultModel -> {
                    String result = ((ResultModel<String>) resultModel).getData();
                    Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                    sweetDialog.success("操作成功").show();
                    btnDelaySwitch.setClickable(true);
                }, error -> {
                    Log.e(TAG, "getDeviceLineStatus: " + error);
                    sweetDialog.error("加载失败!").show();
                    btnDelaySwitch.setClickable(true);
                });
    }

    private void switchLine(int line){
        CommandBean commandBean = new CommandBean();
        StringBuilder commandContent = new StringBuilder();
        switch (lineStatusLocal[line-1]){
            case 0:
                commandContent.append(Command.ON.getCommand()).append(line);
                commandBean.setCommand(commandContent.toString());
                commandBean.setDeviceName(deviceBean.getName());
                commandPresenter.turnOnLine(commandBean)
                        .subscribe(resultModel -> {
                            String result = ((ResultModel<String>) resultModel).getData();
                            Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                            getDeviceLineStatus();
                        }, error -> {
                            Log.e(TAG, "getDeviceLineStatus: " + error);
                            sweetDialog.error("加载失败!").show();
                        });
                break;
            case 1:
                commandContent.append(Command.OFF.getCommand()).append(line);
                commandBean.setCommand(commandContent.toString());
                commandBean.setDeviceName(deviceBean.getName());
                commandPresenter.turnOffLine(commandBean)
                        .subscribe(resultModel -> {
                            String result = ((ResultModel<String>) resultModel).getData();
                            Log.e(TAG, "getDeviceLineStatus: " + result.trim());
                            getDeviceLineStatus();
                        }, error -> {
                            Log.e(TAG, "getDeviceLineStatus: " + error);
                            sweetDialog.error("加载失败!").show();
                        });
                break;
                default:
        }
//        commandContent.append(Command.ALL.getCommand())
//                .append(lineStatusLocal[7])
//                .append(lineStatusLocal[6])
//                .append(lineStatusLocal[5])
//                .append(lineStatusLocal[4])
//                .append(lineStatusLocal[3])
//                .append(lineStatusLocal[2])
//                .append(lineStatusLocal[1])
//                .append(lineStatusLocal[0])
//                .toString();
//        commandBean.setCommand(commandContent.toString());
//        commandBean.setDeviceName(deviceBean.getName());
//        commandPresenter.switchAllLineStatus(commandBean)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(resultModel -> {
//                    String result = ((ResultModel<String>) resultModel).getData();
//                    Log.e(TAG, "getDeviceLineStatus: " + result.trim());
//                    getDeviceLineStatus();
//                }, error -> {
//                    Log.e(TAG, "getDeviceLineStatus: " + error);
//                    sweetDialog.error("加载失败!").show();
//                });
    }

//    private boolean checkSwitchStatus(int line, int[] lineStatusDevice, int[] lineStatusLocal) {
//        return lineStatusDevice[line-1] == lineStatusLocal[line-1];
//    }

    private void onSwitchClick(SwitchButton switchButton, int line) {
        switchButton.toggle();
        lineStatusLocal[line-1] = switchButton.isChecked() ? 1 : 0;
        switchLine(line);
//        if (checkSwitchStatus(line, lineStatusDevice, lineStatusLocal)) {
//            switchButton.setTextColor(Color.BLACK);
//        } else {
//            switchButton.setTextColor(Color.GREEN);
//        }
    }

    @Override
    public void onCheckedChange(SwitchButton switchButton, boolean isChecked) {
        switch (switchButton.getId()){
            case R.id.switch1:
                onSwitchClick(switch1, LINE_1);
                break;
            case R.id.switch2:
                onSwitchClick(switch2, LINE_2);
                break;
            case R.id.switch3:
                onSwitchClick(switch3, LINE_3);
                break;
            case R.id.switch4:
                onSwitchClick(switch4, LINE_4);
                break;
            case R.id.switch5:
                onSwitchClick(switch5, LINE_5);
                break;
            case R.id.switch6:
                onSwitchClick(switch6, LINE_6);
                break;
            case R.id.switch7:
                onSwitchClick(switch7, LINE_7);
                break;
            case R.id.switch8:
                onSwitchClick(switch8, LINE_8);
                break;
                default:
        }
    }

}
