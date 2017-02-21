package cn.com.ichile.pigplayer.core.messages;


import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This is generic PlayerMessage for setDataSource
 */
public abstract class SetDataSourceMessage extends PlayerMessage {

    public SetDataSourceMessage(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.SETTING_DATA_SOURCE;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.DATA_SOURCE_SET;
    }
}
