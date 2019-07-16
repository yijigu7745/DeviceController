package com.gh_hitech.devicecontroller.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.DeviceBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class AreaListHolder extends BaseHolder<AreaBean> {

    @BindView(R.id.area_name) TextView areaName;
    @BindView(R.id.switchButton)
    ImageView deviceStatus;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.list_area, null);
        ButterKnife.bind(this,view);
        return view;
    }

    public AreaListHolder() {
    }

    public AreaListHolder(Context context) {
        super(context);
    }

    @Override
    public void refreshView() {
        if(StringUtils.isNotBlank(mData.getDistrict())){
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： "+mData.getDistrict());
        }else if(StringUtils.isNotBlank(mData.getCity())){
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： "+mData.getCity());
        } else if(StringUtils.isNotBlank(mData.getProvince())){
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： "+mData.getProvince());
        }
//            areaName.setVisibility(View.GONE);
//        }
    }
}
