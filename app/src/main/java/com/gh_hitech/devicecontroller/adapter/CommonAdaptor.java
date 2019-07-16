package com.gh_hitech.devicecontroller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gh_hitech.devicecontroller.holder.BaseHolder;

import java.util.List;

/**
 * @author yijigu
 */
public abstract class CommonAdaptor<Data> extends BaseAdapter implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    protected List<Data> mDatas;
    private AbsListView mListView;

    public CommonAdaptor(AbsListView listView, List<Data> mDatas) {
        this.mDatas = mDatas;
        mListView = listView;
        if (null != listView) {
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);
        }
    }

    /**
     *
     * @return
     */
    protected abstract BaseHolder getHolder();

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Data getItem(int position) {
        if (mDatas != null && position < mDatas.size()) {
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        BaseHolder<Data> holder;
        if (convertView != null && convertView.getTag() instanceof BaseHolder) {
            holder = (BaseHolder<Data>) convertView.getTag();
        } else {
            holder = getHolder();
        }
        holder.setPosition(position);
        holder.setData(mDatas.get(position));
        return holder.getRootView();
    }

    /**
     * 返回listview的header数量
     * @return
     */
    public int getHeaderViewCount() {
        int count = 0;
        if (mListView != null && mListView instanceof ListView) {
            ListView listView = (ListView) mListView;
            count = listView.getHeaderViewsCount();
        }
        return count;
    }

    /**
     * 对onItemClick进行了封装，防止在头部添加了head position的位置有问题
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 此时的position是加上了header的
        position = position - getHeaderViewCount();
        if(position < mDatas.size()) {
            onItemClickInner(view, position, mDatas.get(position));
        }
    }

    /**
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        // 此时的position是加上了header的
        position = position - getHeaderViewCount();
        if(position < mDatas.size()) {
            onItemLongClickInner(view, position, mDatas.get(position));
        }
        return true;
    }

    /**
     * 如果要监听item点击
     * @param view
     * @param position
     * @param data
     */
    public void onItemClickInner(View view, int position, Data data) {

    }

    /**
     * 如果要监听item点击
     * @param view
     * @param position
     * @param data
     */
    public void onItemLongClickInner(View view, int position, Data data) {

    }
}
