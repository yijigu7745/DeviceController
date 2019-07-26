package com.gh_hitech.devicecontroller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.holder.DeviceRecyclerHolder;
import com.gh_hitech.devicecontroller.holder.RvHolder;
import com.gh_hitech.devicecontroller.listener.RvListener;
import com.gh_hitech.devicecontroller.model.DeviceBean;

import java.util.List;

/**
 * @author yijigu
 */
public class DeviceRecyclerAdapter extends AbstractRvAdaptor<DeviceBean> {

    public DeviceRecyclerAdapter(Context context, List<DeviceBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected RvHolder getHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_device, parent, false);
        return new DeviceRecyclerHolder(view, viewType, listener);
    }
}
