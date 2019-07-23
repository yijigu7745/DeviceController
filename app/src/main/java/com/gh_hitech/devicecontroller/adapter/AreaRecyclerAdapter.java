package com.gh_hitech.devicecontroller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.holder.AreaRecyclerHolder;
import com.gh_hitech.devicecontroller.holder.RvHolder;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.AreaBean;

import java.util.List;

/**
 * 区域列表适配器
 * @author yijigu
 */
public class AreaRecyclerAdapter extends AbstractRvAdaptor<AreaBean> {
    public AreaRecyclerAdapter(Context context, List<AreaBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected RvHolder getHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_area,parent,false);
        return new AreaRecyclerHolder(view,viewType,listener);
    }
}
