package cn.com.ichile.topvideonews.util;

import android.util.Log;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/30 - 9:31.
 */
public class Logger {

    private static final boolean isLog = true;

    private static final int level = 5;

    private static final int V = 1;

    private static final int D = 2;

    private static final int I = 3;

    private static final int W = 4;

    private static final int E = 5;

    public static void e(final String TAG, final String message) {
        if (level >= E)
            Log.e(TAG, attachThreadId(message));
    }

    public static void e(final String TAG, final String message, Throwable throwable) {
        if (level >= E)
            Log.e(TAG, attachThreadId(message), throwable);
    }

    public static void w(final String TAG, final String message) {
        if (level >= W)
            Log.w(TAG, attachThreadId(message));
    }

    public static void i(final String TAG, final String message) {
        if (level >= I)
            Log.i(TAG, attachThreadId(message));
    }

    public static void d(final String TAG, final String message) {
        if (level >= D)
            Log.d(TAG, attachThreadId(message));
    }

    public static void v(final String TAG, final String message) {
        if (level >= V)
            Log.v(TAG, attachThreadId(message));
    }

    private static String attachThreadId(String str) {
        return "" + Thread.currentThread().getId() + " " + str;
    }

}
