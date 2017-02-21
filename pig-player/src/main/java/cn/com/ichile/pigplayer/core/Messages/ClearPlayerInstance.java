package cn.com.ichile.pigplayer.core.messages;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage clears MediaPlayer instance that was used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class ClearPlayerInstance extends PlayerMessage {

    public ClearPlayerInstance(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.clearPlayerInstance();
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.CLEARING_PLAYER_INSTANCE;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.PLAYER_INSTANCE_CLEARED;
    }
}
