package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cn.api.message.ContentPreciseQueryRequest;
import cn.com.ichile.topvideonews.App;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.adapter.CollRecyAdapter;
import cn.com.ichile.topvideonews.db.CollectionDao;
import cn.com.ichile.topvideonews.net.DataUtil;
import cn.com.ichile.topvideonews.util.UiUtil;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/7.
 */

public class CollectionActivity extends BaseActivity implements View.OnClickListener {
    private CoordinatorLayout container;
    private CollRecyAdapter collRecyAdapter;

    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activuty_collection);
        initView();
        initRefreshView();
        initRecycleView();
        initData();
    }


    private void initView() {
        container = (CoordinatorLayout) findViewById(R.id.snackbar_container);
    }

    private void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_coll);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollectionActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        collRecyAdapter = new CollRecyAdapter(null, CollectionActivity.this);
        recyclerView.setAdapter(collRecyAdapter);
    }

    private void initRefreshView() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_coll);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorActionBar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UiUtil.showSimpleSnackbar(App.getAppContext(), container, "已刷新", "确定", null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initData() {
        try {
            CollectionDao dao = new CollectionDao(App.getAppContext(), null);
            List<ContentPreciseQueryRequest> requests = dao.getListWithIdUnique();
            DataUtil.getSectionListByIds(collRecyAdapter, requests);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean hasToolBar() {
        return true;
    }

    @Override
    public String setToolBarTitle() {
        return "收藏";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
