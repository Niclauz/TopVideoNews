package cn.com.ichile.topvideonews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.ichile.topvideonews.R;
import cn.sharesdk.framework.ShareSDK;
import cn.update.DownLoadUtils;
import cn.update.DownloadApk;
import cn.update.PackageUtil;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 19:36.
 */

public class SplashActivity extends BaseActivity {
    private static final int onData = 12345;
    private static long staTime;
    private static long curTime;
    private long mD;


    private boolean AUTO_UPDATE = true; // �Ƿ��Զ�����

    private String desc = ""; // title
    private String dlUrl = ""; // downloadUrl

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

        checkVersionUpdate();


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

    protected void download(String url, String title, String appName) {
        DownloadApk.registerBroadcast(this);
        DownloadApk.removeFile(this);
        if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
            DownloadApk.downloadApk(getApplicationContext(), "http://d.wifiwin.cn/apk_dl/index.jsp?sfid=dapian",
                    "����Test", "Test");
        } else {
            DownLoadUtils.getInstance(getApplicationContext()).skipToDownloadManager();
        }
    }

    private void checkVersionUpdate() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                final String json = "";
                JSONObject object;
                try {
                    object = new JSONObject(json);
                    int remoteCode = object.getInt("versionCode");
                    desc = object.getString("desc");
                    dlUrl = object.getString("url");

                    int localCode = PackageUtil.getVersionCode(getApplicationContext());
                    if (remoteCode > localCode) {
                        if (AUTO_UPDATE) {
                            // TODO ����
                            // download();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        DownloadApk.unregisterBroadcast(this);
        super.onDestroy();
    }

    @Override
    public boolean hasToolBar() {
        return false;
    }

    @Override
    public String setToolBarTitle() {
        return null;
    }
}
