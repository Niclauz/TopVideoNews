package cn.com.ichile.topvideonews.util;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/5 - 14:17.
 */

public class StringUtil {

    public static String strOrError(String str) {
        return str == null ? "error" : str;
    }

    public static String strOrNull(String str) {
        return str == null ? "" : str;
    }
}
