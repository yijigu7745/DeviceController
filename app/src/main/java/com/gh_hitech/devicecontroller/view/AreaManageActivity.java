package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.adapter.AreaRecyclerAdapter;
import com.gh_hitech.devicecontroller.base.BaseActivity;
import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.AreaPresenter;
import com.gh_hitech.devicecontroller.presenter.PavilionPresenter;
import com.gh_hitech.devicecontroller.ui.DialogFactory;
import com.gh_hitech.devicecontroller.ui.SheetPopUpWindow;
import com.gh_hitech.devicecontroller.utils.SweetDialog;
import com.gh_hitech.devicecontroller.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.view.IView;

/**
 * 区域管理
 * @author yijigu
 */
public class AreaManageActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerview_1)
    RecyclerView recyclerView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;

    private TextView tvTitle;
    private RelativeLayout layoutRight;

    AreaRecyclerAdapter areaRecyclerAdapter;
    List<AreaBean> areaList = new ArrayList<>();
    List<Map<String,Integer>> countByAreaList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    private int selectPosition = -1;
    AreaPresenter areaPresenter;
    PavilionPresenter pavilionPresenter;
    SheetPopUpWindow popUpWindow;

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
        pavilionPresenter = new PavilionPresenter(this);
        sweetDialog = SweetDialog.builder(this);
        init();
        register();
    }


    private void init() {
        final List<String> menu = new ArrayList<>();
        menu.add("查看警银亭");
        popUpWindow = DialogFactory.createSheetPopUpWindow(context,menu,(pos, value)->{
            switch(value){
                case "查看警银亭":
                    Intent intent = new Intent(AreaManageActivity.this,PavilionListActivity.class);
                    intent.putExtra("area",areaList.get(selectPosition));
                    startActivity(intent);
                    break;
                default:
            }
        });
        areaRecyclerAdapter = new AreaRecyclerAdapter(context,areaList,(id, position)->{
            selectPosition = position;
            popUpWindow.showAtLocation(getCurrentActivity()
                            .findViewById(R.id.area_list),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(areaRecyclerAdapter);
    }

    private void register() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void loadData(){
        sweetDialog.progress("正在加载中...").show();
        areaPresenter.getAreaListByParentCodeLike("3501")
                .subscribe(resultModel -> {
                            areaList.clear();
                            areaList.addAll(((ResultModel<List<AreaBean>>) resultModel).getData());
                            areaRecyclerAdapter.notifyDataSetChanged();
                            sweetDialog.close();
                            ToastUtils.longTimeText(context,"加载成功");
                            swipeRefreshLayout.setRefreshing(false);
                        },error ->{
                            sweetDialog.error("加载失败!").show();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                );
        pavilionPresenter.countByArea("350100")
                .subscribe(resultModel -> {
                    countByAreaList.clear();
                    countByAreaList.addAll(((ResultModel<List<Map<String,Integer>>>)resultModel).getData());
                    for (AreaBean a:areaList) {
                        for (Map<String,Integer> m: countByAreaList) {
                            if(m.containsKey(a.getAreaCode())){
                                a.setPavilionCount(m.get(a.getAreaCode()));
                            }
                        }
                    }
                    areaRecyclerAdapter.notifyDataSetChanged();
                },error ->{
                    sweetDialog.error("加载区域详情失败!").show();
                });
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
