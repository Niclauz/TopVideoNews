package cn.com.ichile.topvideonews;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 19:35.
 */

public class App extends Application {
    private static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        if(Build.VERSION.SDK_INT >= 23) {

        }else{

        }

        Log.i("app","oncreate");

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口

        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("App",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("App","fail--" + s + "--" + s1);
            }
        });
        //ApzIrPbsVMA1T6LB8YWrWIyo55bhbLqmoaxcL-HXMrmS
       // mPushAgent.getRegistrationId();

    }

    public static Context getAppContext() {
        return appContext;
    }
}
