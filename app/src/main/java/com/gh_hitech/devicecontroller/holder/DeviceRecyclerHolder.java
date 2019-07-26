package com.gh_hitech.devicecontroller.holder;

import android.view.View;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.DeviceBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class DeviceRecyclerHolder extends RvHolder<DeviceBean> {
    @BindView(R.id.device_name)
    TextView deviceName;

    public DeviceRecyclerHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(DeviceBean deviceBean, int position) {
        if (StringUtils.isNotBlank(deviceBean.getName())) {
            deviceName.setVisibility(View.VISIBLE);
            deviceName.setText("控制器： " + deviceBean.getName());
        } else {
            deviceName.setVisibility(View.GONE);
        }
    }
}
