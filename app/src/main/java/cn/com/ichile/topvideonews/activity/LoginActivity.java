package cn.com.ichile.topvideonews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.util.HashMap;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.widget.RoundImageView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.login.LoginApi;
import cn.sharesdk.login.OnLoginListener;
import cn.sharesdk.login.UserInfo;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/5 - 16:19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private RoundImageView mRivAvatar;
    private RelativeLayout mBtnWX;
    private RelativeLayout mBtnSina;
    private RelativeLayout mBtnQQ;
    private Context context;


    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_login);
        context = this;

        mRivAvatar = (RoundImageView) findViewById(R.id.riv_login_avatar);
        mBtnQQ = (RelativeLayout) findViewById(R.id.btn_login_qq);
        mBtnSina = (RelativeLayout) findViewById(R.id.btn_login_sina);
        mBtnWX = (RelativeLayout) findViewById(R.id.btn_login_wx);

        mBtnSina.setOnClickListener(this);
        mBtnQQ.setOnClickListener(this);
        mBtnWX.setOnClickListener(this);


        initPlatformList();
    }

    @Override
    public boolean hasToolBar() {
        return false;
    }

    @Override
    public String setToolBarTitle() {
        return null;
    }

    private void initPlatformList() {
        ShareSDK.initSDK(this);
        Platform[] Platformlist = ShareSDK.getPlatformList();
        if (Platformlist != null) {
        }
    }

    /*
     * 演示执行第三方登录/注册的方法
	 * <p>
	 * 这不是一个完整的示例代码，需要根据您项目的业务需求，改写登录/注册回调函数
	 *
	 * @param platformName 执行登录/注册的平台名称，如：SinaWeibo.NAME
	 */
    private void login(String platformName) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处不需要注册
                for (String key : res.keySet()) {
                    Object o = res.get(key);
                    Log.i("000---","key--" + key);
                    Log.i("111---",o.toString());
                }

                UserInfo info = new UserInfo();
                if (platform == QQ.NAME) {
                    info.setUserName((String) res.get("nickname"));
                    info.setUserIcon((String) res.get("figureurl_2"));
                    info.setUserNote("");

                }else if(platform == Wechat.NAME) {

                }else if(platform == SinaWeibo.NAME) {
                    info.setUserName((String) res.get("name"));
                    info.setUserIcon((String) res.get("avatar_hd"));
                    info.setUserNote((String) res.get("description"));
                }


                Intent intent = new Intent();
                intent.putExtra("loginInfo",info);
                setResult(Cons.LOGIN_RESULT, intent);
                finish();
                return false;
            }

            public boolean onRegister(UserInfo info) {
                boolean flag;
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                if (info != null) {
                    flag = true;

                } else {
                    flag = false;
                }
                return flag;
            }
        });
        api.login(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_qq:
                login(QQ.NAME);
                break;
            case R.id.btn_login_sina:
                login(SinaWeibo.NAME);
                break;
            case R.id.btn_login_wx:
                login(Wechat.NAME);
                break;
            default:
                break;
        }
    }
}
