package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.contract.DeviceContract;
import com.gh_hitech.devicecontroller.contract.PavilionContract;
import com.gh_hitech.devicecontroller.dialog.CheckboxDialog;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.IBaseName;
import com.gh_hitech.devicecontroller.model.PavilionBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.DevicePresenter;
import com.gh_hitech.devicecontroller.presenter.PavilionPresenter;
import com.gh_hitech.devicecontroller.utils.Constants;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;
import cn.com.yijigu.rxnetwork.view.IView;

/**
 * 添加设备及警银亭界面
 *
 * @author yijigu
 */
public class AddDeviceActivity extends BaseActivity implements IView, View.OnClickListener {

    final private String TAG = "AddDeviceActivity";
    DeviceContract.Presenter addPresenter;
    PavilionContract.Presenter pavilionPresenter;
    @BindView(R.id.btn_add_device_and_kiosk)
    Button btnAddDeviceAndKiosk;
    @BindView(R.id.btn_add_device_and_bind_kiosk)
    Button btnAddDeviceAndBindKiosk;
    @BindView(R.id.btn_select_kiosk)
    Button btnSelectKiosk;
    /**
     * 添加设备并同时添加警银亭
     */
    @BindView(R.id.et_device_name)
    EditText etDeviceName;
    /**
     * 添加设备并绑定警银亭
     */
    @BindView(R.id.et_device_name2)
    EditText etDeviceName2;
    @BindView(R.id.et_kiosk_name)
    EditText etKioskName;
    @BindView(R.id.et_kiosk_address)
    EditText etKioskAddress;
    /**
     * 需要绑定的警银亭名字
     */
    @BindView(R.id.tv_kiosk_name3)
    TextView bindKioskName;

    SweetDialog sweetDialog;
    List<PavilionBean> pavilionBeanList;
    Long checkId = Constants.NO_KIOSK;

    private TextView tvTitle;
    private RelativeLayout layoutRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        ButterKnife.bind(this);
        addPresenter = new DevicePresenter(this);
        pavilionPresenter = new PavilionPresenter(this);
        sweetDialog = SweetDialog.builder(this);
        register();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        getLayoutInflater().inflate(R.layout.toobar_layout, toolbar);

        //设置回退按钮
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        // toolbar返回事件
        toolbar.setNavigationOnClickListener(view -> AddDeviceActivity.this.finish());

        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("添加面板");

        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetDialog.close();
    }

    private void register() {
        btnAddDeviceAndKiosk.setOnClickListener(this);
        btnAddDeviceAndBindKiosk.setOnClickListener(this);
        btnSelectKiosk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_device_and_kiosk:
                PavilionBean pavilionBean = new PavilionBean();
                pavilionBean.setAddress(etKioskAddress.getText().toString());
                pavilionBean.setName(etKioskName.getText().toString());
                if (StringUtils.isBlank(etKioskName.getText().toString())) {
                    sweetDialog.error("请填写警银亭名称！").show();
                    return;
                }
                if (StringUtils.isBlank(etKioskAddress.getText().toString())) {
                    sweetDialog.error("请填写警银亭地址！").show();
                    return;
                }
                sweetDialog.progress("正在加载...").show();
                pavilionPresenter.addPavilion(pavilionBean)
                        .subscribe(resultModel -> {
                            sweetDialog.success("添加成功").show();
                        }, error -> {
                            sweetDialog.error("连接失败").show();
                            Log.e(TAG, "error: " + error);
                        });
                break;
            case R.id.btn_add_device_and_bind_kiosk:
                pavilionBean = new PavilionBean();
                pavilionBean.setId(checkId);
                DeviceBean deviceBean = new DeviceBean();
                if (StringUtils.isBlank(etDeviceName2.getText().toString())) {
                    sweetDialog.error("请填写设备名！").show();
                    return;
                }
                if (checkId == Constants.NO_KIOSK.longValue()) {
                    sweetDialog.error("请选择绑定警银亭！").show();
                    return;
                }
                deviceBean.setName(etDeviceName2.getText().toString());
                deviceBean.setPavilionBean(pavilionBean);
                sweetDialog.progress("正在加载...").show();
                addPresenter.addDevice(deviceBean)
                        .subscribe(resultModel -> {
                            sweetDialog.success("添加成功").show();
                            Log.i(TAG, "msg: " + resultModel.toString());
                        }, error -> {
                            sweetDialog.error("连接失败").show();
                            Log.e(TAG, "error: " + error);
                        });
                break;
            case R.id.btn_select_kiosk:
                //选择警银亭并将名称显示在前台
                pavilionPresenter.getPavilionList()
                        .subscribe(resultModel -> {
                            pavilionBeanList = ((ResultModel<List<PavilionBean>>) resultModel).getData();
                            Log.i(TAG, "msg: " + resultModel.toString());
                            showPavilionSelectDialog();
                        }, error -> {
                            sweetDialog.error("连接失败").show();
                            Log.e(TAG, "error: " + error);
                        });
            default:
        }
    }

    private void showPavilionSelectDialog() {
        List<IBaseName> list = new ArrayList<>();
        list.addAll(pavilionBeanList);
        CheckboxDialog checkboxDialog = new CheckboxDialog(this);
        checkboxDialog.setSingleListBean(list, checkId);
        checkboxDialog.setOnButtonClickListener(new CheckboxDialog.OnButtonClickListener() {
            @Override
            public void onConfirmationClick() {
                checkboxDialog.setConfirmation();
                if (StringUtils.isNotBlank(checkboxDialog.getId())) {
                    checkId = Long.parseLong(checkboxDialog.getId());
                    bindKioskName.setText(checkboxDialog.getName());
                } else {
                    checkId = Constants.NO_KIOSK;
                    bindKioskName.setText(Constants.NULL_STRING);
                }
            }

            @Override
            public void onCancelClick() {

            }
        });
        checkboxDialog.show();
    }
}
