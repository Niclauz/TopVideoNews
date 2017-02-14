package cn.com.ichile.topvideonews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.api.message.ContentPreciseQueryRequest;
import cn.api.model.ContentBase;
import cn.api.model.ContentMain;
import cn.com.ichile.topvideonews.Cons;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/9.
 */

public class CollectionDao {
    private CusDbHelper mCusDbHelper;
    private SQLiteDatabase db;

    public CollectionDao(Context context, DatabaseErrorHandler databaseErrorHandler) {
        mCusDbHelper = new CusDbHelper(context, databaseErrorHandler);
    }

    public void addListWithId(ContentPreciseQueryRequest request) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content_id", request.getContentId());
        values.put("is_main", request.getIsMian());
        db.insert(Cons.TB_COLLECTION, null, values);
        db.close();
    }

    public void addCollWithIdUnique(ContentBase contentBase) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        String isMian = contentBase instanceof ContentMain ? "1" : "0";
        String sql = "REPLACE INTO " + Cons.TB_COLLECTION_UNIQUE
                + " (content_id,is_main)"
                + " VALUES (" + contentBase.getId() + "," + isMian
                + ")";
        db.execSQL(sql);
        db.close();
    }

    public void addListWithFull(List<ContentMain> collListFull) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (ContentMain contentMain : collListFull) {
            values.put("src_site", contentMain.getSrcSite());
            values.put("title1", contentMain.getTitle1());
            values.put("image1", contentMain.getImage1());
            values.put("play_stream", contentMain.getPlayStreaming());
            db.insert(Cons.TB_COLLECTION_FULL, null, values);
        }
        db.close();
    }

    public ContentPreciseQueryRequest getCollById(ContentBase contentBase) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        boolean distinct = true;
        String[] columns = new String[]{"_id", "content_id", "is_main"};
        String selection = "content_id = ? AND is_main = ?";
        String is_main = contentBase instanceof ContentMain ? "1" : "0";
        String[] args = new String[]{contentBase.getId() + "", is_main};
        String orderBy = "_id DESC";
        Cursor cursor = db.query(distinct, Cons.TB_COLLECTION, columns, selection, args, null, null, orderBy, null);
        cursor.moveToPosition(-1);
        ContentPreciseQueryRequest request = null;
        while (cursor.moveToNext()) {
            request = new ContentPreciseQueryRequest();
            request.setIsMian(cursor.getInt(cursor.getColumnIndex("is_main")));
            request.setContentId(cursor.getInt(cursor.getColumnIndex("content_id")));

        }
        cursor.close();
        db.close();
        return request;
    }

    public ContentPreciseQueryRequest getCollByIdUnique(ContentBase contentBase) throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        boolean distinct = true;
        String[] columns = new String[]{"_id", "content_id", "is_main"};
        String selection = "content_id = ? AND is_main = ?";
        String is_main = contentBase instanceof ContentMain ? "1" : "0";
        String[] args = new String[]{contentBase.getId() + "", is_main};
        String orderBy = "_id DESC";
        Cursor cursor = db.query(distinct, Cons.TB_COLLECTION_UNIQUE, columns, selection, args, null, null, orderBy, null);
        cursor.moveToPosition(-1);
        ContentPreciseQueryRequest request = null;
        while (cursor.moveToNext()) {
            request = new ContentPreciseQueryRequest();
            request.setIsMian(cursor.getInt(cursor.getColumnIndex("is_main")));
            request.setContentId(cursor.getInt(cursor.getColumnIndex("content_id")));

        }
        cursor.close();
        db.close();
        return request;
    }

    public boolean isCollected(ContentBase contentBase) throws Exception {
        ContentPreciseQueryRequest collByIdUnique = getCollByIdUnique(contentBase);
        return collByIdUnique == null ? false : true;
    }

    public List<ContentPreciseQueryRequest> getListWithId() throws Exception {
        ArrayList<ContentPreciseQueryRequest> list = new ArrayList<>();
        db = mCusDbHelper.getReadableDatabase();
        boolean distinct = true;
        String[] columns = new String[]{"_id", "content_id", "is_main"};
        String selection = null;
        String[] args = null;
        String orderBy = "_id DESC";
        Cursor cursor = db.query(distinct, Cons.TB_COLLECTION, columns, selection, args, null, null, orderBy, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            ContentPreciseQueryRequest request = new ContentPreciseQueryRequest();
            request.setContentId(cursor.getInt(cursor.getColumnIndex("content_id")));
            request.setIsMian(cursor.getInt(cursor.getColumnIndex("is_main")));
            list.add(request);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<ContentPreciseQueryRequest> getListWithIdUnique() throws Exception {
        ArrayList<ContentPreciseQueryRequest> list = new ArrayList<>();
        db = mCusDbHelper.getReadableDatabase();
        boolean distinct = true;
        String[] columns = new String[]{"_id", "content_id", "is_main"};
        String selection = null;
        String[] args = null;
        String orderBy = "_id DESC";
        Cursor cursor = db.query(distinct, Cons.TB_COLLECTION_UNIQUE, columns, selection, args, null, null, orderBy, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            ContentPreciseQueryRequest request = new ContentPreciseQueryRequest();
            request.setContentId(cursor.getInt(cursor.getColumnIndex("content_id")));
            request.setIsMian(cursor.getInt(cursor.getColumnIndex("is_main")));
            list.add(request);
        }
        cursor.close();
        db.close();
        return list;
    }


    public List<ContentMain> getListWithFull() {
        ArrayList<ContentMain> list = new ArrayList<>();
        db = mCusDbHelper.getReadableDatabase();
        boolean distinct = true;
        String[] columns = new String[]{"_id", "src_site", "title1", "image1", "play_stream"};
        String selection = null;
        String[] args = null;
        String orderBy = "_id DESC";
        Cursor cursor = db.query(distinct, Cons.TB_COLLECTION_FULL, columns, selection, args, null, null, orderBy, null);
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            ContentMain contentMain = new ContentMain();
            contentMain.setSrcSite(cursor.getString(cursor.getColumnIndex("src_site")));
            contentMain.setTitle1(cursor.getString(cursor.getColumnIndex("title1")));
            contentMain.setImage1(cursor.getString(cursor.getColumnIndex("image1")));
            contentMain.setPlayStreaming(cursor.getString(cursor.getColumnIndex("play_stream")));
            list.add(contentMain);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteCollByIdUnique(ContentBase contentBase) throws Exception {
        int delete = -1;
        db = mCusDbHelper.getWritableDatabase();
        String isMain = contentBase instanceof ContentMain ? "1" : "0";
        String whereClause = "content_id = ? AND is_main = ?";
        String[] args = new String[]{contentBase.getId() + "", isMain};
        delete = db.delete(Cons.TB_COLLECTION_UNIQUE, whereClause, args);
        db.close();
        return delete;
    }

    public void deleteAll() throws Exception {
        db = mCusDbHelper.getWritableDatabase();
        db.delete(Cons.TB_COLLECTION_UNIQUE, null, null);
        db.close();
    }
}
