package cn.com.ichile.topvideonews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import cn.com.ichile.topvideonews.R;
import cn.sharesdk.framework.ShareSDK;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 19:36.
 */

public class SplashActivity extends BaseActivity {
    private static final int onData = 12345;
    private static long staTime;
    private static long curTime;
    private long mD;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case onData:
                    curTime = SystemClock.currentThreadTimeMillis();
                    mD = curTime - staTime;
                    Log.i("ssssss", "----" + mD);
                    if (mD < 3500) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                SystemClock.sleep(3000 - mD);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                            }
                        }.start();
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void baseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
        staTime = SystemClock.currentThreadTimeMillis();

        ShareSDK.initSDK(this);


        new Thread() {
            @Override
            public void run() {
                Message message = mHandler.obtainMessage();
                message.what = onData;

                //TODO 初始化首页数据
                mHandler.sendMessage(message);
            }
        }.start();
    }

    @Override
    public boolean hasToolBar() {
        return false;
    }

    @Override
    public String setToolBarTitile() {
        return null;
    }
}
