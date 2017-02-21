package cn.com.ichile.pigplayer.manager;

import java.util.Arrays;

import cn.com.ichile.pigplayer.core.MsgHandlerThread;
import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.PlayerMsgState;
import cn.com.ichile.pigplayer.core.manager.PlayerItemChangeListener;
import cn.com.ichile.pigplayer.core.manager.PlayerManager;
import cn.com.ichile.pigplayer.core.messages.ClearPlayerInstance;
import cn.com.ichile.pigplayer.core.messages.CreateNewPlayerInstance;
import cn.com.ichile.pigplayer.core.messages.Prepare;
import cn.com.ichile.pigplayer.core.messages.Release;
import cn.com.ichile.pigplayer.core.messages.Reset;
import cn.com.ichile.pigplayer.core.messages.SetUrlDataSourceMessage;
import cn.com.ichile.pigplayer.core.messages.Start;
import cn.com.ichile.pigplayer.core.messages.Stop;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.core.ui.MediaPlayerWrapper;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 * <p>
 * only one player is working in the list
 */

public class SinglePlayerManager implements PlayerManager, MediaPlayerWrapper.MainThreadMediaPlayerListener, PlayerManagerCallback {


    private static final String TAG = "SinglePlayerManager";

    private PigPlayer mCurrentPlayer = null;
    private PlayerItemChangeListener mPlayerItemChangeListener;
    private final MsgHandlerThread mPlayerMsgHandler = new MsgHandlerThread();

    /**
     * default player state
     */
    private PlayerMsgState mCurrentPlayerMsgState = PlayerMsgState.IDLE;


    public SinglePlayerManager(PlayerItemChangeListener playerItemChangeListener) {
        mPlayerItemChangeListener = playerItemChangeListener;
    }

    /**
     * start a new item to play
     * <p>
     * 1. Stop queue processing to have consistent state of queue when posting new messages
     * 2. Check if current player is active.
     * 3. If it is active and already playing current video we do nothing
     * 4. If not active then start new playback
     * 5. Resume stopped queue
     *
     * @param metaData
     * @param pigPlayer
     * @param url
     */
    @Override
    public void startNewPlay(MetaData metaData, PigPlayer pigPlayer, String url) {
        Logger.v(TAG, ">> playNewVideo, videoPlayer " + pigPlayer + ", mCurrentPlayer " + mCurrentPlayer + ", videoPlayerView " + pigPlayer);

        /**
         * 1
         */
        mPlayerMsgHandler.pauseQueueProcessing(TAG);

        boolean isCurrentPlayerActive = mCurrentPlayer == pigPlayer;
        boolean isReadyToPlay = mCurrentPlayer != null && url.equals(mCurrentPlayer.getVideoUrlDataSource());
        Logger.v(TAG, "playNewVideo, isAlreadyPlayingTheFile " + isReadyToPlay);
        Logger.v(TAG, "playNewVideo, currentPlayerIsActive " + isCurrentPlayerActive);

        /**
         * 2
         */
        if (isCurrentPlayerActive) {
            /**
             * 3
             */
            if (!(isInPlayCallbackState() && isReadyToPlay)) {
                startNewPlayCallback(metaData, pigPlayer, url);
            }
        } else {
            /**
             * 4
             */
            startNewPlayCallback(metaData, pigPlayer, url);
        }

        mPlayerMsgHandler.resumeQueueProcessing(TAG);
        Logger.v(TAG, "<< playNewVideo, videoPlayer " + pigPlayer + ", videoUrl " + url);
    }


    /**
     * stop play callback id any one exists
     */
    @Override
    public void stopPlayCallback() {
        Logger.v(TAG, ">> stopAnyPlayback, mCurrentPlayerState " + mCurrentPlayerMsgState);
        mPlayerMsgHandler.pauseQueueProcessing(TAG);
        mPlayerMsgHandler.clearAllPendingMsg(TAG);

        stopResetReleaseClearCurrentPlayer();

        mPlayerMsgHandler.resumeQueueProcessing(TAG);
    }

    /**
     * stop current play callback and reset current media player
     */
    @Override
    public void resetMediaPlayer() {
        Logger.v(TAG, ">> resetMediaPlayer, mCurrentPlayerState " + mCurrentPlayerMsgState);
        mPlayerMsgHandler.pauseQueueProcessing(TAG);
        mPlayerMsgHandler.clearAllPendingMsg(TAG);

        resetReleaseClearCurrentPlayer();

        mPlayerMsgHandler.resumeQueueProcessing(TAG);
        Logger.v(TAG, "<< resetMediaPlayer, mCurrentPlayerState " + mCurrentPlayerMsgState);
    }

    @Override
    public void onVideoSizeChangedMainThread(int width, int height) {
        //// TODO: 2017/2/16
    }

    @Override
    public void onVideoPreparedMainThread() {
        //// TODO: 2017/2/16
    }

    @Override
    public void onVideoCompletionMainThread() {
        mCurrentPlayerMsgState = PlayerMsgState.PLAYBACK_COMPLETED;
    }

    @Override
    public void onErrorMainThread(int what, int extra) {
        //// TODO: 2017/2/16 handle those error!!!
        mCurrentPlayerMsgState = PlayerMsgState.ERROR;
    }

    @Override
    public void onBufferingUpdateMainThread(int percent) {
        //// TODO: 2017/2/16 notify buffering changed!
    }

    @Override
    public void onVideoStoppedMainThread() {
        //// TODO: 2017/2/16 should notify ui !! ???
    }

    /**
     * call this when new player becomes active
     *
     * @param currentItemMetaData
     * @param newPigPlayer
     */
    @Override
    public void setCurrentItem(MetaData currentItemMetaData, PigPlayer newPigPlayer) {
        mCurrentPlayer = newPigPlayer;
        mPlayerItemChangeListener.onPlayerItemChanged(currentItemMetaData);
    }

    @Override
    public void setVideoPlayerState(PigPlayer newPigPlayer, PlayerMsgState playerMessageState) {
        mCurrentPlayerMsgState = playerMessageState;
    }

    @Override
    public PlayerMsgState getCurrentPlayerState() {
        return mCurrentPlayerMsgState;
    }

    private boolean isInPlayCallbackState() {
        boolean flag = mCurrentPlayerMsgState == PlayerMsgState.STARTED || mCurrentPlayerMsgState == PlayerMsgState.STARTING;
        return flag;
    }

    private void startNewPlayCallback(MetaData metaData, PigPlayer pigPlayer, String url) {
        //// TODO: 2017/2/16 need to be removed!!!
        pigPlayer.addMediaPlayerListener(this);

        mPlayerMsgHandler.clearAllPendingMsg(TAG);

        stopResetReleaseClearCurrentPlayer();
        setNewViewForPlayCallback(metaData, pigPlayer);
        startPlayCallback(pigPlayer, url);
    }

    /**
     * stop current playing player callback
     */
    private void stopResetReleaseClearCurrentPlayer() {
        Logger.v(TAG, "stopResetReleaseClearCurrentPlayer, mCurrentPlayerState " + mCurrentPlayerMsgState + ", mCurrentPlayer " + mCurrentPlayer);

        switch (mCurrentPlayerMsgState) {
            case SETTING_NEW_PLAYER:
            case IDLE:

            case CREATING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CREATED:

            case CLEARING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CLEARED:
                // in these states player is stopped
                break;
            case INITIALIZED:
            case PREPARING:
            case PREPARED:
            case STARTING:
            case STARTED:
            case PAUSING:
            case PAUSED:
                mPlayerMsgHandler.addSingleMsg(new Stop(mCurrentPlayer, this));
                //FALL-THROUGH

            case SETTING_DATA_SOURCE:
            case DATA_SOURCE_SET:
                /** if we don't reset player in this state, will will get 0;0 from {@link android.media.MediaPlayer.OnVideoSizeChangedListener}.
                 *  And this TextureView will never recover */
            case STOPPING:
            case STOPPED:
            case ERROR: // reset if error
            case PLAYBACK_COMPLETED:
                mPlayerMsgHandler.addSingleMsg(new Reset(mCurrentPlayer, this));
                //FALL-THROUGH
            case RESETTING:
            case RESET:
                mPlayerMsgHandler.addSingleMsg(new Release(mCurrentPlayer, this));
                //FALL-THROUGH
            case RELEASING:
            case RELEASED:
                mPlayerMsgHandler.addSingleMsg(new ClearPlayerInstance(mCurrentPlayer, this));
                break;
            case END:
                throw new RuntimeException("unhandled " + mCurrentPlayerMsgState);
        }
    }

    public void resetReleaseClearCurrentPlayer() {
        Logger.v(TAG, "resetReleaseClearCurrentPlayer, mCurrentPlayerState " + mCurrentPlayerMsgState + ", mCurrentPlayer " + mCurrentPlayer);

        switch (mCurrentPlayerMsgState) {
            case SETTING_NEW_PLAYER:
            case IDLE:

            case CREATING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CREATED:

            case SETTING_DATA_SOURCE:
            case DATA_SOURCE_SET:

            case CLEARING_PLAYER_INSTANCE:
            case PLAYER_INSTANCE_CLEARED:
                break;
            case INITIALIZED:
            case PREPARING:
            case PREPARED:
            case STARTING:
            case STARTED:
            case PAUSING:
            case PAUSED:
            case STOPPING:
            case STOPPED:
            case ERROR: // reset if error
            case PLAYBACK_COMPLETED:
                mPlayerMsgHandler.addSingleMsg(new Reset(mCurrentPlayer, this));
                //FALL-THROUGH
            case RESETTING:
            case RESET:
                mPlayerMsgHandler.addSingleMsg(new Release(mCurrentPlayer, this));
                //FALL-THROUGH
            case RELEASING:
            case RELEASED:
                mPlayerMsgHandler.addSingleMsg(new ClearPlayerInstance(mCurrentPlayer, this));
                break;
            case END:
                throw new RuntimeException("unhandled " + mCurrentPlayerMsgState);
        }
    }

    private void setNewViewForPlayCallback(MetaData currentItemMetaData, PigPlayer videoPlayerView) {
        Logger.v(TAG, "setNewViewForPlayback, currentItemMetaData " + currentItemMetaData + ", videoPlayer " + videoPlayerView);
        mPlayerMsgHandler.addSingleMsg(new SetNewViewForPlayCallback(currentItemMetaData, videoPlayerView, this));
    }

    private void startPlayCallback(PigPlayer videoPlayerView, String videoUrl) {
        Logger.v(TAG, "startPlayback");

        mPlayerMsgHandler.addMsgList(Arrays.asList(
                new CreateNewPlayerInstance(videoPlayerView, this),
                new SetUrlDataSourceMessage(videoPlayerView, videoUrl, this),
                new Prepare(videoPlayerView, this),
                new Start(videoPlayerView, this)
        ));
    }
}
