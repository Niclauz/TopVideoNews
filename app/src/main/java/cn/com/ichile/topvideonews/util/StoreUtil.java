package cn.com.ichile.topvideonews.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import cn.api.model.ContentBase;
import cn.api.model.ContentMain;
import cn.api.model.ContentSub;
import cn.com.ichile.topvideonews.App;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/30 - 9:31.
 */

public class StoreUtil {
    private static final String CONTENT_SP = "VideoNews";
    private static final String CONTENT_TYPE = "content_type";
    private static final String CONTENT_ID = "=content_id";
    private static SharedPreferences content_sp = App.getAppContext().getSharedPreferences(CONTENT_SP, Context.MODE_PRIVATE);

    //contentMian-1,contentSub-0
    public static void addCollection(ContentBase content, long content_id) {
        int isMian = 1;
        if (content instanceof ContentMain) {
            isMian = 1;
        } else if (content instanceof ContentSub) {
            isMian = 0;
        }
        content_sp.edit().putInt(String.valueOf(content_id), isMian).commit();
    }

    public static HashMap<Long,Integer> getCollection() {
        Map<String, Integer> all = (Map<String, Integer>) content_sp.getAll();
        HashMap<Long, Integer> map = new HashMap<>();
        for(String key : all.keySet()) {
            map.put(Long.getLong(key),all.get(key));
        }
        return map;
    }

}
