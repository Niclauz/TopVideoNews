package cn.com.ichile.pigplayer.core.messages;

import android.media.MediaPlayer;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage calls {@link MediaPlayer#stop()} on the instance that is used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class Stop extends PlayerMessage {
    public Stop(PigPlayer videoView, PlayerManagerCallback callback) {
        super(videoView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.stop();
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.STOPPING;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return PlayerMsgState.STOPPED;
    }
}
