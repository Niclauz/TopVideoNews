package cn.com.ichile.playertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/3/17 - 9:37.
 */

public class MenuActivity extends Activity implements View.OnClickListener {
    private Button mBtnRecy;
    private Button mBtnSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mBtnRecy = (Button) findViewById(R.id.btn_recy);
        mBtnSingle = (Button) findViewById(R.id.btn_single);
        mBtnRecy.setOnClickListener(this);
        mBtnSingle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recy:
                startActivity(MainActivity.class, MenuActivity.this);
                break;
            case R.id.btn_single:
                startActivity(SingleActivity.class, MenuActivity.this);
                break;

        }
    }

    private void startActivity(Class clazz, Activity activity) {
        Intent intent = new Intent(activity, clazz);
        startActivity(intent);
    }
}
