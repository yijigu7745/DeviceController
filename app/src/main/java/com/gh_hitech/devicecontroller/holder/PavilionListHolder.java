package com.gh_hitech.devicecontroller.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.model.DeviceBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class PavilionListHolder extends BaseHolder<DeviceBean.PavilionBean> {

    @BindView(R.id.pavilion_name) TextView pavilionName;
    @BindView(R.id.switchButton)
    ImageView deviceStatus;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pavilion, null);
        ButterKnife.bind(this,view);
        return view;
    }

    public PavilionListHolder() {
    }

    public PavilionListHolder(Context context) {
        super(context);
    }

    @Override
    public void refreshView() {
        if(StringUtils.isNotBlank(mData.getName())){
            pavilionName.setVisibility(View.VISIBLE);
            pavilionName.setText("警银亭： "+mData.getName());
        }else{
            pavilionName.setVisibility(View.GONE);
        }
    }
}
