package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.adapter.DeviceRecyclerAdapter;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.dialog.CheckboxDialog;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.IBaseName;
import com.gh_hitech.devicecontroller.model.PavilionBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.DevicePresenter;
import com.gh_hitech.devicecontroller.presenter.PavilionPresenter;
import com.gh_hitech.devicecontroller.ui.DialogFactory;
import com.gh_hitech.devicecontroller.ui.SheetPopUpWindow;
import com.gh_hitech.devicecontroller.utils.SweetDialog;
import com.gh_hitech.devicecontroller.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;
import cn.com.yijigu.rxnetwork.view.IView;

/**
 * 设备列表
 * @author yijigu
 */
public class DeviceListActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "DeviceListActivity";
    @BindView(R.id.recycleview_1)
    RecyclerView recyclerView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;
    DeviceRecyclerAdapter deviceRecyclerAdapter;
    PavilionPresenter pavilionPresenter;
    List<DeviceBean> deviceList = new ArrayList<>();
    List<PavilionBean> pavilionList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    DevicePresenter devicePresenter;
    private int selectPosition = -1;
    private PavilionBean pavilionFromIntent;
    private long selectPavilionId;

    private TextView tvTitle;
    private RelativeLayout layoutRight;
    private SheetPopUpWindow popUpWindow;

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
        pavilionPresenter = new PavilionPresenter(this);
        sweetDialog = SweetDialog.builder(this);
        init();
        register();
    }

    private void register() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void init() {
        pavilionFromIntent = (PavilionBean) getIntent().getSerializableExtra("pavilion");
        final List<String> menu = new ArrayList<>();
        menu.add("控制面板");
        menu.add("修改设备所属警银亭");
        menu.add("删除设备");
        popUpWindow = DialogFactory.createSheetPopUpWindow(context,menu,(pos, value)->{
            switch(value){
                case "控制面板":
                    Intent intent = new Intent(DeviceListActivity.this,ControlActivity.class);
                    intent.putExtra("deviceBean",deviceList.get(selectPosition));
                    startActivity(intent);
                    break;
                case "修改设备所属警银亭":
                    selectPavilionDialog();
                    break;
                case "删除设备":
                    deleteConfirm();
                    break;
                default:
            }
        });
        deviceRecyclerAdapter = new DeviceRecyclerAdapter(context,deviceList,(id, position) ->{
            selectPosition = position;
            popUpWindow.showAtLocation(getCurrentActivity()
                            .findViewById(R.id.device_list),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(deviceRecyclerAdapter);
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
                            if(pavilionFromIntent != null){
                                for (DeviceBean d:((ResultModel<List<DeviceBean>>) resultModel).getData()) {
                                    if((d.getPavilionBean() != null)
                                            && (pavilionFromIntent.getId().equals(d.getPavilionBean().getId()))){
                                        deviceList.add(d);
                                    }
                                }
                            }else {
                                deviceList.addAll(((ResultModel<List<DeviceBean>>) resultModel).getData());
                            }
                            deviceRecyclerAdapter.notifyDataSetChanged();
                            sweetDialog.close();
                            ToastUtils.longTimeText(context,"加载成功");
                            swipeRefreshLayout.setRefreshing(false);
                        },error ->{
                            Log.e(TAG, "loadData: "+error);
                            sweetDialog.error("加载失败!").show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                );
        pavilionPresenter.getPavilionList()
                .subscribe(resultModel -> {
                            pavilionList.clear();
                            pavilionList.addAll(((ResultModel<List<PavilionBean>>) resultModel).getData());
                        },error ->{
                            sweetDialog.error("加载警银亭失败!").show();
                        }
                );
    }


    private void selectPavilionDialog(){
        List<IBaseName> pavilion = new ArrayList<>();
        pavilion.addAll(pavilionList);
        final CheckboxDialog checkboxDialog = new CheckboxDialog(context);
        checkboxDialog.setSingleListBean(pavilion,selectPavilionId);
        checkboxDialog.setOnButtonClickListener(new CheckboxDialog.OnButtonClickListener() {
            @Override
            public void onConfirmationClick() {
                checkboxDialog.setConfirmation();
                if(StringUtils.isNotBlank(checkboxDialog.getId())) {
                    selectPavilionId = Long.parseLong(checkboxDialog.getId());
                }
                updateDevicePavilion();
            }

            @Override
            public void onCancelClick() {

            }
        });
        checkboxDialog.show();
    }

    private void updateDevicePavilion(){
        if(pavilionList != null){
            for (PavilionBean pavilionBean:pavilionList) {
                if(pavilionBean.getIid().longValue() == selectPavilionId){
                    DeviceBean deviceBean = deviceList.get(selectPosition);
                    deviceBean.setPavilionBean(pavilionBean);
                    devicePresenter.updateDevice(deviceBean)
                            .subscribe(resultModel -> {
                                sweetDialog.success("修改成功").show();
                                loadData();
                            },error ->{
                                sweetDialog.success("修改失败").show();
                            });
                }
            }
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetDialog.close();
    }
}
