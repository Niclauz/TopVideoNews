package cn.com.ichile.pigplayer.core.messages;

import android.media.MediaPlayer;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * This PlayerMessage calls {@link MediaPlayer#start()} on the instance that is used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class Start extends PlayerMessage {

    private static final String TAG = Start.class.getSimpleName();

    private PlayerMsgState mResultPlayerMessageState;

    public Start(PigPlayer videoPlayerView, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {

        PlayerMsgState currentState = getCurrentState();
        Logger.d(TAG, "currentState " + currentState);

        switch (currentState) {
            case SETTING_NEW_PLAYER:
            case IDLE:
            case INITIALIZED:
            case PREPARING:
            case PREPARED:
            case RELEASING:
            case RELEASED:
            case RESETTING:
            case RESET:
            case CLEARING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CLEARED:
            case CREATING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CREATED:
            case SETTING_DATA_SOURCE:
            case DATA_SOURCE_SET:
            case PLAYBACK_COMPLETED:
            case END:

                throw new RuntimeException("unhandled current state " + currentState);

            case STARTING:
                currentPlayer.start();
                mResultPlayerMessageState = PlayerMsgState.STARTED;
                break;

            case ERROR:
                mResultPlayerMessageState = PlayerMsgState.ERROR;
                break;
            case STARTED:
            case PAUSING:
            case PAUSED:
            case STOPPING:
            case STOPPED:
                // TODO: probably need to handle this
                throw new RuntimeException("unhandled current state " + currentState);
        }
    }

    @Override
    protected PlayerMsgState stateBefore() {
        PlayerMsgState result = null;

        PlayerMsgState currentState = getCurrentState();
        Logger.d(TAG, "stateBefore, currentState " + currentState);
        switch (currentState) {

            case PREPARED:
                result = PlayerMsgState.STARTING;
                break;

            case SETTING_NEW_PLAYER:
            case IDLE:
            case INITIALIZED:
            case PREPARING:
            case RELEASING:
            case RELEASED:
            case RESETTING:
            case RESET:
            case CLEARING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CLEARED:
            case CREATING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CREATED:
            case SETTING_DATA_SOURCE:
            case DATA_SOURCE_SET:
            case PLAYBACK_COMPLETED:
            case END:
            case STARTING:
                throw new RuntimeException("unhandled current state " + currentState);

            case ERROR:
                result = PlayerMsgState.ERROR;
                break;
            case STARTED:
            case PAUSING:
            case PAUSED:
            case STOPPING:
            case STOPPED:
                // TODO: probably need to handle this
                throw new RuntimeException("unhandled current state " + currentState);

        }
        return result;
    }

    @Override
    protected PlayerMsgState stateAfter() {
        return mResultPlayerMessageState;
    }
}
