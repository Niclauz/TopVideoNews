package cn.com.ichile.pigplayer.core.messages;

import android.media.MediaPlayer;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage calls {@link MediaPlayer#release()} on the instance that is used inside {@link PigPlayer}
 */
public class Release extends PlayerMessage {

    public Release(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.release();
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.RELEASING;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.RELEASED;
    }
}
