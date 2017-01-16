package cn.com.ichile.topvideonews.util;

import android.app.Activity;
import android.content.Intent;

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
}
