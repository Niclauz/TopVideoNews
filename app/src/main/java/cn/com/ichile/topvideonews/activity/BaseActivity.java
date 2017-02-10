package cn.com.ichile.topvideonews.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umeng.message.PushAgent;

import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.util.StringUtil;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/6 - 14:04.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseOnCreate(savedInstanceState);
        if (hasToolBar()) {
            ImageButton button = (ImageButton) findViewById(R.id.ib_appbar_back);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            TextView textView = (TextView) findViewById(R.id.tv_toolbar_title);
            textView.setText(StringUtil.strOrNull(setToolBarTitle()));
        }
        PushAgent.getInstance(this).onAppStart();
    }

    public abstract void baseOnCreate(@Nullable Bundle savedInstanceState);

    public abstract boolean hasToolBar();

    public abstract String setToolBarTitle();
}
