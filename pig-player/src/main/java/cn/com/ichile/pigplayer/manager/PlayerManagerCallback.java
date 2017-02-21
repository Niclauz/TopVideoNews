package cn.com.ichile.pigplayer.manager;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.core.meta.MetaData;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 */

public interface PlayerManagerCallback {
    void setCurrentItem(MetaData currentItemMetaData, PigPlayer newPigPlayer);

    void setVideoPlayerState(PigPlayer newPigPlayer, PlayerMsgState playerMessageState);

    PlayerMsgState getCurrentPlayerState();
}
