package com.gh_hitech.devicecontroller.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gh_hitech.devicecontroller.listener.RvListener;

/**
 * @author yijigu
 */
public abstract class RvHolder<T> extends RecyclerView.ViewHolder {
    private RvListener mListener;

    public RvHolder(View itemView, int type, RvListener listener) {
        super(itemView);
        this.mListener = listener;
        itemView.setOnClickListener(v -> mListener.onItemClick(v.getId(), getAdapterPosition()));
    }

    /**
     *
     * @param t
     * @param position
     */
    public abstract void bindHolder(T t, int position);
}
