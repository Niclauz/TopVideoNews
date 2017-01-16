package cn.com.ichile.topvideonews.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.com.ichile.topvideonews.Cons;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/30 - 9:49.
 */

public class CusDbHelper extends SQLiteOpenHelper {

    public CusDbHelper(Context context,DatabaseErrorHandler errorHandler) {
        super(context, Cons.DB_NAME, null, Cons.DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //缓存内容表
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Cons.TB_ORIGINAL + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "video_token VARCHAR(20),"//视频唯一id
                + "video_url TEXT,"//视频地址
                + "video_title TEXT,"//标题
                + "video_image TEXT,"//大图
                + "video_thumb TEXT,"//小图
                + "video_src TEXT)");//内容源

        //播放历史
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Cons.TB_HISTORY + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "video_token VARCHAR(20),"//视频唯一id
                + "video_url TEXT,"//视频地址
                + "video_title TEXT,"//标题
                + "video_image TEXT,"//大图
                + "video_thumb TEXT,"//小图
                + "video_src TEXT,"//内容源
                + "create_time TIMESTAMP NOT NULL DEFAULT (DATETIME('now','localtime')))");

        //收藏
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Cons.TB_FAVERITE + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "video_token VARCHAR(20),"//视频唯一id
                + "video_url TEXT,"//视频地址
                + "video_title TEXT,"//标题
                + "video_image TEXT,"//大图
                + "video_thumb TEXT,"//小图
                + "video_src TEXT,"//内容源
                + "create_time TIMESTAMP NOT NULL DEFAULT (DATETIME('now','localtime')))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
