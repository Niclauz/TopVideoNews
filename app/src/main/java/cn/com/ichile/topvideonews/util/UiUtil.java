package cn.com.ichile.topvideonews.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.ichile.topvideonews.R;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/10 - 19:07.
 */

public class UiUtil {

    public static void startActivity(Activity currentActivity, Class targetClass) {
        Intent intent = new Intent(currentActivity, targetClass);
        currentActivity.startActivity(intent);
    }

    public static void startActivityAndFinish(Activity currentActivity, Class targetClass) {
        Intent intent = new Intent(currentActivity, targetClass);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }

    public static void showSimpleSnackbar (Context context, View view, String msg, String actionDesc, View.OnClickListener onClickListener) {
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("确定", onClickListener);
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.colorActionBar));
        View snackbarView = snackbar.getView();
        TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        Button button = (Button) snackbarView.findViewById(R.id.snackbar_action);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(18);
        button.setTextColor(context.getResources().getColor(R.color.colorWhite));
        snackbar.show();
    }

}
