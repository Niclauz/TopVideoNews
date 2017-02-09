package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.ichile.topvideonews.App;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.util.UiUtil;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/7.
 */

public class CollectionActivity extends BaseActivity implements View.OnClickListener {
    private CoordinatorLayout container;
    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activuty_collection);
        initView();
        initRefreshView();
        initRecycleView();
    }

    private void initView() {
        container = (CoordinatorLayout) findViewById(R.id.snackbar_container);
    }

    private void initRecycleView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_coll);
    }

    private void initRefreshView() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_coll);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorActionBar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                UiUtil.showSimpleSnackbar(App.getAppContext(),container,"已刷新","确定",null);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }


    @Override
    public boolean hasToolBar() {
        return true;
    }

    @Override
    public String setToolBarTitile() {
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
