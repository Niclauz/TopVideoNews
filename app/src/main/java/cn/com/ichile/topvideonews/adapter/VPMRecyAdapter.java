package cn.com.ichile.topvideonews.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.api.model.Content;
import cn.com.ichile.topvideonews.adapter.items.BaseVideoItem;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/14.
 */

public class VPMRecyAdapter extends BaseRecycleAdapter<Content> implements OnNetDataCallback,View.OnClickListener {

    private List<BaseVideoItem> items;
    VPMRecyAdapter(List list) {
        super(list);
        items = list;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(Object... data) {

    }

    @Override
    public void onMore(Object... data) {

    }

    @Override
    public void onError(Object... error) {

    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }


}
