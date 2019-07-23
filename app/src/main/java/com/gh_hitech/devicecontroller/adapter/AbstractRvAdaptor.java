package com.gh_hitech.devicecontroller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gh_hitech.devicecontroller.holder.RvHolder;
import com.gh_hitech.devicecontroller.listener.RvListener;

import java.util.List;

/**
 * RecyclerView的适配器
 * @author yijigu
 */
public abstract class AbstractRvAdaptor<T>  extends RecyclerView.Adapter<RvHolder>{
    protected List<T> mList;
    protected Context mContext;
    protected RvListener listener;
    protected LayoutInflater mInflater;

    public AbstractRvAdaptor(Context context, List<T> list, RvListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mList = list;
        this.listener = listener;
    }

    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    protected abstract RvHolder getHolder(ViewGroup parent, int viewType);

}
