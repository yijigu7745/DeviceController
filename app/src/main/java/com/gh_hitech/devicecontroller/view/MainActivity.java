package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.model.CardListBean;
import com.gh_hitech.devicecontroller.presenter.CardListPresenter;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.functions.Consumer;

/**
 * @author yijigu
 */
public class MainActivity extends BaseActivity implements IView, View.OnClickListener {

    public static final String TAG = "MainActivity";

    @BindView(R.id.pavilion_manage)
    TextView btnManagePavilion;
    @BindView(R.id.device_manage)
    TextView btnManageDevice;
    @BindView(R.id.area_manage)
    TextView btnManageArea;
    @BindView(R.id.other_manage)
    TextView btnManageOther;
    SweetDialog sweetDialog;
    private Context mContext;

    private TextView tvTitle;
    private RelativeLayout layoutRight;

    CardListPresenter cardListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        sweetDialog = SweetDialog.builder(mContext);
        init();
        register();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        getLayoutInflater().inflate(R.layout.toobar_layout, toolbar);
        //设置回退按钮
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.color.blue);
        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("控制器管理");
        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetDialog.close();
    }

    private void init() {
        cardListPresenter = new CardListPresenter(this);
    }

    private void register() {

        cardListPresenter.getCardList().subscribe((Consumer<CardListBean>) resultModel ->{
            if(resultModel != null){
                for(CardListBean.CardsBean cardsBean:resultModel.getCards()){
                    for(CardListBean.CardsBean.CardGroupBean cardGroupBean :cardsBean.getCard_group()){
                        Log.i(TAG, "UserId:"+cardGroupBean.getUser().getId()+"---UserName:"+cardGroupBean.getUser().getName());
                    }
                }
            }
        });
//        btnManageDevice.setOnClickListener(this);
//        btnManageArea.setOnClickListener(this);
//        btnManagePavilion.setOnClickListener(this);
//        btnManageOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.device_manage:
                intent = new Intent(mContext, DeviceListActivity.class);
                startActivity(intent);
                break;
            case R.id.pavilion_manage:
                intent = new Intent(mContext, PavilionListActivity.class);
                startActivity(intent);
                break;
            case R.id.other_manage:
                break;
            case R.id.area_manage:
                intent = new Intent(mContext, AreaManageActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }
}
