package cn.com.ichile.pigplayer.manager;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.core.messages.PlayerMessage;
import cn.com.ichile.pigplayer.core.meta.MetaData;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 */

public class SetNewViewForPlayCallback extends PlayerMessage {
    private MetaData mCurrentMetaData;
    private PigPlayer mCurrentPlayer;
    private PlayerManagerCallback mCurrentPlayerManagerCallback;

    public SetNewViewForPlayCallback(MetaData currentMetaData, PigPlayer currentPlayer, PlayerManagerCallback callback) {
        super(currentPlayer, callback);
        mCurrentMetaData = currentMetaData;
        mCurrentPlayer = currentPlayer;
        mCurrentPlayerManagerCallback = callback;
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        mCurrentPlayerManagerCallback.setCurrentItem(mCurrentMetaData, mCurrentPlayer);
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.SETTING_NEW_PLAYER;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.IDLE;
    }
}
