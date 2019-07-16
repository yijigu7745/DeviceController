package com.gh_hitech.devicecontroller.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.ui.ImageAndTextView;
import com.gh_hitech.devicecontroller.ui.SwitchButton;
import com.gh_hitech.devicecontroller.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class DeviceListHolder extends BaseHolder<DeviceBean> {

    @BindView(R.id.device_name) TextView deviceName;
    @BindView(R.id.switchButton)
    ImageView deviceStatus;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.list_device, null);
        ButterKnife.bind(this,view);
        return view;
    }

    public DeviceListHolder() {
    }

    public DeviceListHolder(Context context) {
        super(context);
    }

    @Override
    public void refreshView() {
        if(StringUtils.isNotBlank(mData.getName())){
            deviceName.setVisibility(View.VISIBLE);
            deviceName.setText("控制器： "+mData.getName());
        }else{
            deviceName.setVisibility(View.GONE);
        }
    }
}
