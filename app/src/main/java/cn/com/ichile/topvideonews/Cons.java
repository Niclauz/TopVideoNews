package cn.com.ichile.topvideonews;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 20:48.
 */
public class Cons {
    public static final int LOGIN_RESULT = 12;


    //DB
    public static final String DB_NAME = "tvn";
    public static final int DB_VERSION = 1;
    public static final String TB_HISTORY = "history";
    public static final String TB_FAVERITE = "faverite";
    public static final String TB_ORIGINAL = "original";



    public static final String HOT = "/ok.json";
    public static final String TV = "/ok2.json";
    public static final String RECOMMEND = "/ok3.json";
    public static final String BASE = "http://test.biedese.cn/VideoNews";

    public static String HOT_URL = BASE + HOT;

    public static String TV_URL = BASE + TV;

    public static String RECOMMEND_URL = BASE + RECOMMEND;
}
