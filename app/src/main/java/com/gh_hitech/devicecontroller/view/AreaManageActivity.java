package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.adapter.CommonAdaptor;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.holder.AreaListHolder;
import com.gh_hitech.devicecontroller.holder.BaseHolder;
import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.AreaPresenter;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 区域管理
 * @author yijigu
 */
public class AreaManageActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.gridview_1)
    GridView gridView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;

    private TextView tvTitle;
    private RelativeLayout layoutRight;

    private CommonAdaptor<AreaBean> areaListAdaptor;
    List<AreaBean> areaList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    AreaPresenter areaPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        getLayoutInflater().inflate(R.layout.toobar_layout, toolbar);

        //设置回退按钮
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        // toolbar返回事件
        toolbar.setNavigationOnClickListener(view -> AreaManageActivity.this.finish());

        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("区域管理");

        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_manage);
        context = this;
        ButterKnife.bind(this);
        areaPresenter = new AreaPresenter(this);
        sweetDialog = SweetDialog.builder(this);
        init();
        register();
    }


    private void init() {
        areaListAdaptor = new CommonAdaptor<AreaBean>(gridView,areaList) {
            @Override
            protected BaseHolder getHolder() {
                return new AreaListHolder(context);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return super.onItemLongClick(parent, view, position, id);
            }

        };
        gridView.setNumColumns(2);
        gridView.setAdapter(areaListAdaptor);
    }

    private void register() {
        swipeRefreshLayout.setOnRefreshListener(this);
        //GridView与SwipeRefreshLayout下拉冲突解决
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getCheckedItemPosition() == 0) {

                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = gridView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        swipeRefreshLayout.setEnabled(true);
                    } else {
                        swipeRefreshLayout.setEnabled(false);
                    }
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }

                // 判断滚动到底部
                if (view.getLastVisiblePosition() == (view.getCount() - 1)) {

                }
            }
        });
    }

    private void loadData(){
        sweetDialog.progress("正在加载中...").show();
        areaPresenter.getAreaListByParentCodeLike("3501").subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultModel -> {
                            areaList.clear();
                            areaList.addAll(((ResultModel<List<AreaBean>>) resultModel).getData());
                            areaListAdaptor.notifyDataSetChanged();
                            sweetDialog.success("数据加载成功").show();
                            swipeRefreshLayout.setRefreshing(false);
                        },error ->{
                            sweetDialog.error("加载失败!").show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
