package cn.com.ichile.pigplayer.core.manager;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.meta.MetaData;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 * <p>
 * A general interface for playermanager
 * PROVIDE:
 * 1、start video play callback
 * 2、stop existing video play callback
 * 3、reset media player
 * <p>
 * https://github.com/danylovolokh/VideoPlayerManager
 */

public interface PlayerManager<T extends MetaData> {


    void startNewPlay(T metaData, PigPlayer pigPlayer, String url);

    void stopPlayCallback();

    void resetMediaPlayer();
}
