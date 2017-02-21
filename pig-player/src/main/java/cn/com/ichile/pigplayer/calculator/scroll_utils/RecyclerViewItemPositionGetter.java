package cn.com.ichile.pigplayer.calculator.scroll_utils;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.ichile.pigplayer.utils.Logger;

/**
 * This class is an API for {@link .ListItemsVisibilityCalculator}
 * Using this class is can access all the data from RecyclerView
 * Created by danylo.volokh on 06.01.2016.
 */
public class RecyclerViewItemPositionGetter implements ItemsPositionGetter {

    private static final String TAG = RecyclerViewItemPositionGetter.class.getSimpleName();

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public RecyclerViewItemPositionGetter(LinearLayoutManager layoutManager, RecyclerView recyclerView) {
        mLayoutManager = layoutManager;
        mRecyclerView = recyclerView;
    }

    @Override
    public View getChildAt(int position) {

        Logger.v(TAG, "getChildAt, mRecyclerView.getChildCount " + mRecyclerView.getChildCount());
        Logger.v(TAG, "getChildAt, mLayoutManager.getChildCount " + mLayoutManager.getChildCount());


        View view = mLayoutManager.getChildAt(position);


        Logger.v(TAG, "mRecyclerView getChildAt, position " + position + ", view " + view);
        Logger.v(TAG, "mLayoutManager getChildAt, position " + position + ", view " + mLayoutManager.getChildAt(position));


        return view;
    }

    @Override
    public int indexOfChild(View view) {
        int indexOfChild = mRecyclerView.indexOfChild(view);
        Logger.v(TAG, "indexOfChild, " + indexOfChild);
        return indexOfChild;
    }

    @Override
    public int getChildCount() {
        int childCount = mRecyclerView.getChildCount();

        Logger.v(TAG, "getChildCount, mRecyclerView " + childCount);
        Logger.v(TAG, "getChildCount, mLayoutManager " + mLayoutManager.getChildCount());

        return childCount;
    }

    @Override
    public int getLastVisiblePosition() {
        return mLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getFirstVisiblePosition() {
        Logger.v(TAG, "getFirstVisiblePosition, findFirstVisibleItemPosition " + mLayoutManager.findFirstVisibleItemPosition());
        return mLayoutManager.findFirstVisibleItemPosition();
    }
}
