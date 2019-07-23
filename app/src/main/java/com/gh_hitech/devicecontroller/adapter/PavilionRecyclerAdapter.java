package com.gh_hitech.devicecontroller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.holder.PavilionRecyclerHolder;
import com.gh_hitech.devicecontroller.holder.RvHolder;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.PavilionBean;

import java.util.List;

/**
 * @author yijigu
 */
public class PavilionRecyclerAdapter extends AbstractRvAdaptor<PavilionBean> {

    public PavilionRecyclerAdapter(Context context, List<PavilionBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected RvHolder getHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_pavilion, parent, false);
        return new PavilionRecyclerHolder(view, viewType, listener);
    }

}
