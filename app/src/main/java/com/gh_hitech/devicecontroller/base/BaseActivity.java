package com.gh_hitech.devicecontroller.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.helper.ToolBarHelper;
import com.gh_hitech.devicecontroller.utils.ApplicationUtils;
import com.gyf.barlibrary.ImmersionBar;

/**
 * @author yijigu
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ImmersionBar mImmersionBar;
    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar;
    private static BaseActivity mForegroundActivity = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ApplicationUtils.getApplication().addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        setSupportActionBar(toolbar);
        onCreateCustomToolBar(toolbar) ;
    }

    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
    }

    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
    }

    @Override
    protected void onResume() {
        mForegroundActivity = this;
        super.onResume();
    }

    public static BaseActivity getCurrentActivity(){
        return mForegroundActivity;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将Activity从管理器移除
        ApplicationUtils.getApplication().addActivity(this);
    }

}
