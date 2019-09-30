package com.gh_hitech.devicecontroller.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.login.contract.LoginContract;
import com.gh_hitech.devicecontroller.login.loginbean.DeviceBindInfoBean;
import com.gh_hitech.devicecontroller.retrofit.StringUtils;
import com.gh_hitech.devicecontroller.utils.Constants;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mLoginPresenter;

    public static final String TAG = "LoginFragment";

    private EditText etUserName;
    private EditText etPassword;
    private EditText etDeviceName;
    private EditText etDeviceAlias;
    private EditText etRemark;
    private TextView tvIMEICode;
    private Button btnLogin;
    private Button btnGetMission;

    private SweetDialog sweetDialog;

    public LoginFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        etUserName = (EditText) root.findViewById(R.id.et_user_name);
        etPassword = (EditText) root.findViewById(R.id.et_password);
        etDeviceName = (EditText) root.findViewById(R.id.et_device_name);
        etDeviceAlias = (EditText) root.findViewById(R.id.et_device_alias);
        etRemark = (EditText) root.findViewById(R.id.et_remark);
        tvIMEICode = (TextView) root.findViewById(R.id.tv_imei_code);
        btnLogin = (Button) root.findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(__ -> login());
        btnGetMission = (Button) root.findViewById(R.id.btn_get_mission);
//        btnGetMission.setOnClickListener(__ -> getMission());
        sweetDialog = SweetDialog.builder(getContext());
        if (Constants.TEST_MODE) {
            etUserName.setText("test");
            etPassword.setText("123456");
            etDeviceAlias.setText("lkb");
        }
        return root;
    }

    private void login() {
        DeviceBindInfoBean deviceBindInfoBean = new DeviceBindInfoBean();
        if (StringUtils.isNotBlank(etUserName.getText().toString())) {
            deviceBindInfoBean.setBind_user(etUserName.getText().toString());
        } else {
            sweetDialog.error("请输入用户名").show();
            return;
        }
        if (StringUtils.isNotBlank(etPassword.getText().toString())) {
            deviceBindInfoBean.setBind_pass(etPassword.getText().toString());
        } else {
            sweetDialog.error("请输入密码").show();
            return;
        }
        if (StringUtils.isNotBlank(etDeviceAlias.getText().toString())) {
            deviceBindInfoBean.setDevice_alias(etDeviceAlias.getText().toString());
        }
        mLoginPresenter.login(deviceBindInfoBean);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mLoginPresenter = checkNotNull(presenter, "loginPresenter can not be null!");
    }

    @Override
    public Context getmContext() {
        return this.getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLoginPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mLoginPresenter.unsubscribe();
    }
}
