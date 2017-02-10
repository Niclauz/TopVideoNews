package cn.com.ichile.topvideonews.util;

import android.content.Context;

import cn.api.message.ContentPreciseQueryRequest;
import cn.api.model.ContentBase;
import cn.api.model.ContentMain;
import cn.api.model.ContentSub;
import cn.com.ichile.topvideonews.db.CollectionDao;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/30 - 9:31.
 */

public class StoreUtil {
    private static final String CONTENT_SP = "VideoNews";
    private static final String CONTENT_TYPE = "content_type";
    private static final String CONTENT_ID = "=content_id";

    //contentMian-1,contentSub-0
    public static void addCollection(Context context,ContentBase content, long content_id) throws Exception{
        int isMian = 1;
        if (content instanceof ContentMain) {
            isMian = 1;
        } else if (content instanceof ContentSub) {
            isMian = 0;
        }
        CollectionDao dao = new CollectionDao(context, null);
        ContentPreciseQueryRequest request = new ContentPreciseQueryRequest();
        request.setIsMian(isMian);
        request.setContentId(content_id);
        dao.addListWithId(request);
    }



}
