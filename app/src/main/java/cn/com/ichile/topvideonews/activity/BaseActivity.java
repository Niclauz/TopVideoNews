package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.message.PushAgent;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/6 - 14:04.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseOnCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

    public abstract void baseOnCreate(@Nullable Bundle savedInstanceState);
}
