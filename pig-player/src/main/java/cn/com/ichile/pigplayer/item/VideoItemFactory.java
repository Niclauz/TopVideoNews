package cn.com.ichile.pigplayer.item;

import android.app.Activity;

import cn.com.ichile.pigplayer.core.manager.PlayerManager;
import cn.com.ichile.pigplayer.core.meta.MetaData;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/3/15 - 15:12.
 */

public class VideoItemFactory {
    public static BaseVideoItem createItemFromNet(Activity activity, PlayerManager<MetaData> playerManager,String title, String coverImg, String author, String url) {
        return new NetVideoItem(activity, playerManager,title, coverImg, author, url);
    }
}
