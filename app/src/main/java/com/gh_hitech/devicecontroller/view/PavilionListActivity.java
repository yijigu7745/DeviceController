package com.gh_hitech.devicecontroller.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.gh_hitech.devicecontroller.holder.BaseHolder;
import com.gh_hitech.devicecontroller.holder.PavilionListHolder;
import com.gh_hitech.devicecontroller.model.PavilionBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.presenter.PavilionPresenter;
import com.gh_hitech.devicecontroller.ui.DialogFactory;
import com.gh_hitech.devicecontroller.ui.SheetPopUpWindow;
import com.gh_hitech.devicecontroller.utils.SweetDialog;
import com.gh_hitech.devicecontroller.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.view.IView;

/**
 * 设备列表
 * @author yijigu
 */
public class PavilionListActivity extends BaseActivity implements IView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.gridview_1)
    GridView gridView;
    @BindView(R.id.reload_data)
    SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdaptor<PavilionBean> pavilionListAdaptor;
    List<PavilionBean> pavilionList = new ArrayList<>();
    SweetDialog sweetDialog;
    private Context context;
    PavilionPresenter pavilionPresenter;
    private int selectPosition = -1;

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

        final List<String> menu = new ArrayList<>();
        menu.add("");
        popUpWindow = DialogFactory.createTimeSelectDialog(context,menu,(pos,value)->{
            switch(value){
                case "":
                    break;
                    default:
            }
        });
    }

    private void init() {
        pavilionListAdaptor = new CommonAdaptor<PavilionBean>(gridView,pavilionList) {
            @Override
            protected BaseHolder getHolder() {
                return new PavilionListHolder(context);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                deleteConfirm();
                return super.onItemLongClick(parent, view, position, id);
            }

        };
        gridView.setNumColumns(2);
        gridView.setAdapter(pavilionListAdaptor);
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
                            pavilionList.addAll(((ResultModel<List<PavilionBean>>) resultModel).getData());
                            pavilionListAdaptor.notifyDataSetChanged();
                            sweetDialog.close();
                            ToastUtils.longTimeText(context,"加载成功");
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
