package cn.com.ichile.topvideonews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.domain.VideoBean;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/30 - 10:40.
 */

public class OriginalDao {
    private CusDbHelper mCusDbHelper;
    private SQLiteDatabase db;

    public OriginalDao(Context context, DatabaseErrorHandler databaseErrorHandler) {
        mCusDbHelper = new CusDbHelper(context, databaseErrorHandler);
    }

    /**
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long add(VideoBean videoBean) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("video_token", videoBean.getToken());
        values.put("video_url", videoBean.getUrl());
        values.put("video_title", videoBean.getTitle());
        values.put("video_image", videoBean.getImage());
        values.put("video_thumb", videoBean.getThumb());
        values.put("video_src", videoBean.getAuthor());
        long insert = db.insert(Cons.TB_ORIGINAL, null, values);
        db.close();
        return insert;
    }

    /**
     * the row ID of the newly inserted row, or -1 if an error occurred
     * @param list
     * @return
     * @throws Exception
     */
    public long addAll(List<VideoBean> list) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        long insert = -1;
        for (VideoBean videoBean : list) {
            ContentValues values = new ContentValues();
            values.put("video_token", videoBean.getToken());
            values.put("video_url", videoBean.getUrl());
            values.put("video_title", videoBean.getTitle());
            values.put("video_image", videoBean.getImage());
            values.put("video_thumb", videoBean.getThumb());
            values.put("video_src", videoBean.getAuthor());
            insert = db.insert(Cons.TB_ORIGINAL, null, values);
        }
        db.close();
        return insert;
    }

    /**
     * the number of rows affected if a whereClause is passed in,
     * 0 otherwise. To remove all rows and get a count pass "1" as the whereClause
     */
    public int deleteAll() throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        String whereClause = null;
        String[] args = null;
        int delete = db.delete(Cons.TB_ORIGINAL, whereClause, args);
        db.close();
        return delete;
    }

    public VideoBean queryByToken(String token) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        boolean distinct = true;
        String table = Cons.TB_ORIGINAL;
        String[] columns = null;
        String selection = "token=?";
        String[] selectionArgs = new String[]{String.valueOf(token)};
        String groupBy = null;
        String having = null;
        String orderBy = "_id DESC";
        String limit = null;
        Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        VideoBean videoBean = new VideoBean();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            videoBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
            videoBean.setToken(cursor.getString(cursor.getColumnIndex("video_token")));
            videoBean.setImage(cursor.getString(cursor.getColumnIndex("video_image")));
            videoBean.setAuthor(cursor.getString(cursor.getColumnIndex("video_src")));
            videoBean.setThumb(cursor.getString(cursor.getColumnIndex("video_thumb")));
            videoBean.setTitle(cursor.getString(cursor.getColumnIndex("video_title")));
            videoBean.setUrl(cursor.getString(cursor.getColumnIndex("video_url")));
        }
        cursor.close();
        db.close();
        return videoBean;
    }

    /**
     * @param count query COUNT items
     */
    public List<VideoBean> queryList(int count) throws Exception {
        List<VideoBean> list = queryNextList(count, 0);
        return list;
    }

    public List<VideoBean> queryAll() throws Exception {
        List<VideoBean> list = queryNextList(0, 0);
        return list;
    }

    /**
     * @param count
     * @param currentPosition query next COUNT items from CURRENTPOSITION
     */
    public List<VideoBean> queryNextList(int count, int currentPosition) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        boolean distinct = true;
        String table = Cons.TB_ORIGINAL;
        String[] columns = null;

        String selection;
        String[] selectionArgs;
        if (currentPosition <= 0) {
            selection = null;
            selectionArgs = null;
        } else {
            selection = "_id >= ?";
            selectionArgs = new String[]{String.valueOf(currentPosition)};
        }
        String groupBy = null;
        String having = null;
        String orderBy = "_id";
        String limit = count <= 0 ? null : String.valueOf(count);
        Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        List<VideoBean> list = new ArrayList<>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            VideoBean videoBean = new VideoBean();
            videoBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
            videoBean.setToken(cursor.getString(cursor.getColumnIndex("video_token")));
            videoBean.setImage(cursor.getString(cursor.getColumnIndex("video_image")));
            videoBean.setAuthor(cursor.getString(cursor.getColumnIndex("video_src")));
            videoBean.setThumb(cursor.getString(cursor.getColumnIndex("video_thumb")));
            videoBean.setTitle(cursor.getString(cursor.getColumnIndex("video_title")));
            videoBean.setUrl(cursor.getString(cursor.getColumnIndex("video_url")));
            Log.i("daodaodao",videoBean.toString());
            list.add(videoBean);
        }
        cursor.close();
        db.close();
        return list;
    }

}
