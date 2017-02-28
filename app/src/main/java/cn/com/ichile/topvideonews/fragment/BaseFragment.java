package cn.com.ichile.topvideonews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.api.model.Type1;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/29 - 14:17.
 */

public abstract class BaseFragment extends Fragment {
    //当页面可见时数据加载
    protected boolean isVisiable;
    protected int typeCode;
    protected String typeName;
    protected int type;

    @Override
    public void setArguments(Bundle args) {
        Type1 type = (Type1) args.getSerializable("type");
        if (type != null) {
            typeCode = type.getType1Code();
            typeName = type.getName();
        }
        super.setArguments(args);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflateView(inflater, container, savedInstanceState);
        initView(view);
        return view;
    }

    @Nullable
    public abstract View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);


    public abstract OnNetDataCallback getItemAdapter();

    public abstract void initView(View view);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisiable = true;
            onVisiable();
        } else {
            isVisiable = false;
            onInviable();
        }
    }

    protected void onVisiable() {
        lazyLoad();
    }


    protected void onInviable() {

    }

    protected abstract void lazyLoad();

}
