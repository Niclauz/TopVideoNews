package cn.com.ichile.pigplayer.core.messages;

import android.media.MediaPlayer;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.core.ui.MediaPlayerWrapper;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * This PlayerMessage calls {@link MediaPlayer#prepare()} on the instance that is used inside {@link PigPlayer}
 */
public class Prepare extends PlayerMessage {

    private static final String TAG = Prepare.class.getSimpleName();

    private PlayerMsgState mResultPlayerMessageState;

    public Prepare(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {

        currentPlayer.prepare();

        MediaPlayerWrapper.State resultOfPrepare = currentPlayer.getCurrentState();
        Logger.v(TAG, "resultOfPrepare " + resultOfPrepare);

        switch (resultOfPrepare) {
            case IDLE:
            case INITIALIZED:
            case PREPARING:
            case STARTED:
            case PAUSED:
            case STOPPED:
            case PLAYBACK_COMPLETED:
            case END:
                throw new RuntimeException("unhandled state " + resultOfPrepare);

            case PREPARED:
                mResultPlayerMessageState = PlayerMsgState.PREPARED;
                break;

            case ERROR:
                mResultPlayerMessageState = PlayerMsgState.ERROR;
                break;
        }
    }

    @Override
    protected PlayerMsgState stateBefore() {
        return PlayerMsgState.PREPARING;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return mResultPlayerMessageState;
    }
}
