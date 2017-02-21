package cn.com.ichile.pigplayer.core.manager;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.meta.MetaData;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 * <p>
 * a general interface for items in adapter of any list
 */

public interface VideoItem {
    void playNewVideo(MetaData currentItemMetaData, PigPlayer player, PlayerManager<MetaData> playerManager);

    void stopPlayback(PlayerManager playerManager);
}
