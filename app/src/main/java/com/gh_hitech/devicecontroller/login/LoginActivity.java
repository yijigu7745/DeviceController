package com.gh_hitech.devicecontroller.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.login.presenter.LoginPresenter;
import com.gh_hitech.devicecontroller.utils.ActivityUtils;

public class LoginActivity extends AppCompatActivity {

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.login_frame);

        if(loginFragment == null){
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),loginFragment,R.id.login_frame);
        }

        mLoginPresenter = new LoginPresenter(loginFragment);
    }

}
