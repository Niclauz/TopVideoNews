package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.com.ichile.topvideonews.R;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/7.
 */

public class MoreAvtivity extends BaseActivity implements View.OnClickListener {
    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activuty_more);
    }

    @Override
    public boolean hasToolBar() {
        return true;
    }

    @Override
    public String setToolBarTitle() {
        return "更多";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
