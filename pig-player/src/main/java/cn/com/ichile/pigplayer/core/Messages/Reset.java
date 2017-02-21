package cn.com.ichile.pigplayer.core.messages;

import android.media.MediaPlayer;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;


/**
 * This PlayerMessage calls {@link MediaPlayer#reset()} on the instance that is used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class Reset extends PlayerMessage {
    public Reset(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.reset();
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.RESETTING;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.RESET;
    }
}
