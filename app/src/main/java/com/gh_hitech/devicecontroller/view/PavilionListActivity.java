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
import com.gh_hitech.devicecontroller.adapter.PavilionRecyclerAdapter;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.dialog.CheckboxDialog;
import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.IBaseName;
import com.gh_hitech.devicecontroller.model.PavilionBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.AreaPresenter;
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
public class PavilionListActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "PavilionListActivity";
    @BindView(R.id.recycler_1)
    RecyclerView recyclerView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;
    PavilionRecyclerAdapter pavilionRecyclerAdapter;
    List<PavilionBean> pavilionList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    PavilionPresenter pavilionPresenter;
    AreaPresenter areaPresenter;
    List<AreaBean> areaList = new ArrayList<>();
    private long selectAreaId;
    private int selectPosition = -1;
    private AreaBean areaFromIntent;

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
        toolbar.setNavigationOnClickListener(view -> PavilionListActivity.this.finish());
        // 设置标题
        tvTitle = toolbar.findViewById(R.id.tv_title);
        tvTitle.setText("警银亭管理");
        // 右键点击
        layoutRight = toolbar.findViewById(R.id.right_layout);
        layoutRight.setVisibility(View.GONE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pavilion_list);
        ButterKnife.bind(this);
        context = this;
        pavilionPresenter = new PavilionPresenter(this);
        areaPresenter = new AreaPresenter(this);
        sweetDialog = SweetDialog.builder(this);
        areaFromIntent = (AreaBean) getIntent().getSerializableExtra("area");
        init();
        register();
    }

    private void register() {
        swipeRefreshLayout.setOnRefreshListener(this);
        final List<String> menu = new ArrayList<>();
        menu.add("添加控制器");
        menu.add("查看控制器");
        menu.add("修改区域分组");
        menu.add("删除警银亭");
        popUpWindow = DialogFactory.createSheetPopUpWindow(context,menu,(pos,value)->{
            switch(value){
                case "添加控制器":
                    Intent intent = new Intent(this,AddDeviceActivity.class);
                    startActivity(intent);
                    break;
                case "查看控制器":
                    intent = new Intent(this,DeviceListActivity.class);
                    intent.putExtra("pavilion",pavilionList.get(selectPosition));
                    startActivity(intent);
                    break;
                case "修改区域分组":
                    selectAreaDialog();
                    break;
                case "删除警银亭":
                    deleteConfirm();
                    break;
                    default:
            }
        });
    }

    private void init() {
        pavilionRecyclerAdapter = new PavilionRecyclerAdapter(this, pavilionList,
                (id, position) -> {
                    selectPosition = position;
                    popUpWindow.showAtLocation(getCurrentActivity()
                            .findViewById(R.id.pavilion_list),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(pavilionRecyclerAdapter);
    }

    private void deleteConfirm() {
        sweetDialog.waring("提示",
                "是否删除警银亭"+ pavilionList.get(selectPosition).getName()+"?",
                false,true)
                .setConfirmClickListener(sweetAlertDialog -> deletePavilion(pavilionList.get(selectPosition))).show();
    }

    private void deletePavilion(PavilionBean pavilionBean) {
        pavilionPresenter.deletePavilion(pavilionBean.getId())
                .subscribe(resultModel -> {
                    sweetDialog.success("删除成功").show();
                    loadData();
                },error -> {
                    sweetDialog.error("删除失败!").show();
                    swipeRefreshLayout.setRefreshing(false);});
    }

    private void loadData(){
        sweetDialog.progress("正在加载中...").show();
        pavilionPresenter.getPavilionList()
                .subscribe(resultModel -> {
                            pavilionList.clear();
                            if(areaFromIntent != null){
                                for (PavilionBean p:((ResultModel<List<PavilionBean>>) resultModel).getData()) {
                                    if((p.getPavilionArea() != null)
                                            && (areaFromIntent.getId() == p.getPavilionArea().getId())){
                                        pavilionList.add(p);
                                    }
                                }
                            }else {
                                pavilionList.addAll(((ResultModel<List<PavilionBean>>) resultModel).getData());
                            }
                            pavilionRecyclerAdapter.notifyDataSetChanged();
                            sweetDialog.close();
                            ToastUtils.longTimeText(context,"加载成功");
                            swipeRefreshLayout.setRefreshing(false);
                        },error ->{
                            sweetDialog.error("加载警银亭列表失败!").show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                );
        areaPresenter.getAreaListByParentCodeLike("3501")
                .subscribe(resultModel ->{
                    areaList.clear();
                    areaList.addAll(((ResultModel<List<AreaBean>>)resultModel).getData());
                },error -> {
                    sweetDialog.error("加载区域失败!").show();
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void selectAreaDialog(){
        List<IBaseName> area = new ArrayList<>();
        area.addAll(areaList);
        final CheckboxDialog checkboxDialog = new CheckboxDialog(context);
        checkboxDialog.setSingleListBean(area,selectAreaId);
        checkboxDialog.setOnButtonClickListener(new CheckboxDialog.OnButtonClickListener() {
            @Override
            public void onConfirmationClick() {
                checkboxDialog.setConfirmation();
                if(StringUtils.isNotBlank(checkboxDialog.getId())) {
                    selectAreaId = Long.parseLong(checkboxDialog.getId());
                }
                updatePavilionArea();
            }

            @Override
            public void onCancelClick() {

            }
        });
        checkboxDialog.show();
    }

    private void updatePavilionArea(){
        if(areaList != null){
            for (AreaBean areaBean:areaList) {
                if(areaBean.getIid().longValue() == selectAreaId){
                    PavilionBean pavilionBean = pavilionList.get(selectPosition);
                    pavilionBean.setPavilionArea(areaBean);
                    pavilionPresenter.updatePavilion(pavilionBean)
                            .subscribe(resultModel ->{
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
    public void onRefresh() {
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sweetDialog.close();
    }
}
