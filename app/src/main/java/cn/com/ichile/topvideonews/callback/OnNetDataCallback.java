package cn.com.ichile.topvideonews.callback;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/29 - 14:24.
 */

public interface OnNetDataCallback{
    void onSuccess(Object... data);

    void onError(Object... error);

    void onProgress(int progress);
}
