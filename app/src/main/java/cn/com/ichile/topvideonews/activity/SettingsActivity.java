package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.ichile.topvideonews.R;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/7.
 */

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_settings);

    }

    @Override
    public boolean hasToolBar() {
        return true;
    }

    @Override
    public String setToolBarTitle() {
        return "设置";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
