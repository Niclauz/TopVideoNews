package cn.com.ichile.topvideonews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.topvideonews.inter.BaseAdapterInter;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/9 - 11:13.
 */

public class BaseRecycleAdapter<T> extends RecyclerView.Adapter implements BaseAdapterInter {

    protected List<T> mDataList;

    BaseRecycleAdapter(List<T> list) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (list != null)
            mDataList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //
    }

    protected View getView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    @Override
    public int getItemCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    public void add(int position, T item) {
        mDataList.add(position, item);
        notifyItemInserted(position);
    }

    public void addMore(List<T> list) {
        int startPosition = mDataList.size();
        mDataList.addAll(list);
        notifyItemRangeInserted(startPosition, mDataList.size());
    }

    public List<T> getData() {
        return mDataList;
    }

    public void setData(List<T> data) {
        mDataList = data;
        notifyDataSetChanged();
    }
}
