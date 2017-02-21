package cn.com.ichile.pigplayer.core.messages;


import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * This is generic interface for PlayerMessage
 */
public abstract class PlayerMessage implements Msg {

    private static final String TAG = PlayerMessage.class.getSimpleName();
    private final PigPlayer mCurrentPlayer;
    private final PlayerManagerCallback mCallback;

    public PlayerMessage(PigPlayer currentPlayer, PlayerManagerCallback callback) {
        mCurrentPlayer = currentPlayer;
        mCallback = callback;
    }

    protected final PlayerMsgState getCurrentState() {
        return mCallback.getCurrentPlayerState();
    }

    @Override
    public final void polledFromQueue() {
        mCallback.setVideoPlayerState(mCurrentPlayer, stateBefore());
    }

    @Override
    public final void messageFinished() {
        mCallback.setVideoPlayerState(mCurrentPlayer, stateAfter());
    }

    public final void runMessage() {
        Logger.v(TAG, ">> runMessage, " + getClass().getSimpleName());
        performAction(mCurrentPlayer);
        Logger.v(TAG, "<< runMessage, " + getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    protected abstract void performAction(PigPlayer currentPlayer);

    protected abstract PlayerMsgState stateBefore();

    protected abstract PlayerMsgState stateAfter();

}
