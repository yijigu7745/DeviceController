package com.gh_hitech.devicecontroller.holder;

import android.view.View;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.PavilionBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class PavilionRecyclerHolder extends RvHolder<PavilionBean> {
    @BindView(R.id.pavilion_name)
    TextView pavilionName;
    @BindView(R.id.pavilion_area)
    TextView pavilionArea;

    public PavilionRecyclerHolder(View itemView, int type, RvListener listener) {
        super(itemView, type, listener);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindHolder(PavilionBean pavilionBean, int position) {
        if (StringUtils.isNotBlank(pavilionBean.getName())) {
            pavilionName.setVisibility(View.VISIBLE);
            pavilionName.setText("警银亭： " + pavilionBean.getName());
        } else {
            pavilionName.setVisibility(View.GONE);
        }
        if (pavilionBean.getPavilionArea() != null && StringUtils.isNotBlank(pavilionBean.getPavilionArea().getAreaName())) {
            pavilionArea.setVisibility(View.VISIBLE);
            pavilionArea.setText("所属区域： " + pavilionBean.getPavilionArea().getAreaName());
        } else {
            pavilionArea.setVisibility(View.GONE);
        }
    }
}
