package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.gh_hitech.devicecontroller.holder.BaseHolder;
import com.gh_hitech.devicecontroller.holder.DeviceListHolder;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.DevicePresenter;
import com.gh_hitech.devicecontroller.utils.SweetDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 设备列表
 * @author yijigu
 */
public class DeviceListActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.gridview_1)
    GridView gridView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdaptor<DeviceBean> deviceListAdaptor;
    List<DeviceBean> deviceList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    DevicePresenter devicePresenter;
    private int selectPosition = -1;

    private TextView tvTitle;
    private RelativeLayout layoutRight;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        getLayoutInflater().inflate(R.layout.toobar_layout, toolbar);

        //设置回退按钮
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        // toolbar返回事件
        toolbar.setNavigationOnClickListener(view -> DeviceListActivity.this.finish());

        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("设备管理");

        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);
        context = this;
        devicePresenter = new DevicePresenter(this);
        sweetDialog = SweetDialog.builder(this);
        init();
        register();
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

    private void init() {
        deviceListAdaptor = new CommonAdaptor<DeviceBean>(gridView,deviceList) {
            @Override
            protected BaseHolder getHolder() {
                return new DeviceListHolder(context);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DeviceListActivity.this,ControlActivity.class);
                intent.putExtra("deviceBean",deviceList.get(position));
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                deleteConfirm();
//                if(deviceList.get(selectPosition).getId() != -1L){
//                }
                return super.onItemLongClick(parent, view, position, id);
            }

        };
        gridView.setNumColumns(2);
        gridView.setAdapter(deviceListAdaptor);
    }

    private void deleteConfirm() {
        sweetDialog.waring("提示",
                "是否删除设备"+ deviceList.get(selectPosition).getName()+"?",
                false,true)
                .setConfirmClickListener(sweetAlertDialog -> deleteDevice(deviceList.get(selectPosition))).show();
    }


    private void deleteDevice(DeviceBean deviceBean) {
        devicePresenter.deleteDevice(deviceBean.getId())
                .subscribe(resultModel -> {
                    sweetDialog.success("删除成功").show();
                    loadData();
                },error -> {
                    sweetDialog.error("删除失败!").show();
                    swipeRefreshLayout.setRefreshing(false);});
    }

    private void loadData(){
        sweetDialog.progress("正在加载中...").show();
        devicePresenter.getDeviceList()
                .subscribe(resultModel -> {
                            deviceList.clear();
                            deviceList.addAll(((ResultModel<List<DeviceBean>>) resultModel).getData());
                            deviceListAdaptor.notifyDataSetChanged();
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
