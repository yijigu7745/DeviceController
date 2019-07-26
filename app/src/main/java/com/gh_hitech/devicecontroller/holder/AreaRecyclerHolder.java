package com.gh_hitech.devicecontroller.holder;

import android.view.View;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.AreaBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * 区域列表Holder
 *
 * @author yijigu
 */
public class AreaRecyclerHolder extends RvHolder<AreaBean> {
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.pavilion_count)
    TextView pavilionCount;

    public AreaRecyclerHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(AreaBean areaBean, int position) {
        if (StringUtils.isNotBlank(areaBean.getDistrict())) {
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： " + areaBean.getDistrict());
        } else if (StringUtils.isNotBlank(areaBean.getCity())) {
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： " + areaBean.getCity());
        } else if (StringUtils.isNotBlank(areaBean.getProvince())) {
            areaName.setVisibility(View.VISIBLE);
            areaName.setText("区域： " + areaBean.getProvince());
        }
        if (areaBean.getPavilionCount() != 0) {
            pavilionCount.setVisibility(View.VISIBLE);
            pavilionCount.setText("亭体数量：" + areaBean.getPavilionCount());
        } else {
            pavilionCount.setVisibility(View.GONE);
        }
    }
}
