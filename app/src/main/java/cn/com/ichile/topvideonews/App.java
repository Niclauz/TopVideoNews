package cn.com.ichile.topvideonews;

import android.app.Application;
import android.content.Context;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 19:35.
 */

public class App {
    private static Context appContext;
    private static Application appApplication;


    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static Application getApplication() {
        return appApplication;
    }

    public static void setApplication(Application application) {
        appApplication = application;
    }
}
