package cn.com.ichile.topvideonews;

import android.app.Application;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/3/31 - 16:27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        App.setApplication(this);
        App.setAppContext(this);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口

        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("App", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("App", "fail--" + s + "--" + s1);
            }
        });
    }
}
