package cn.com.ichile.pigplayer.core.ui;

import android.util.Pair;

import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/15.
 */

public class ReadyForPlaybackIndicator {
    private static final String TAG = "ReadyForPlaybackIndicator";

    private Pair<Integer, Integer> mVideoSize;
    private boolean mSurfaceTextureAvailable;
    private boolean mFailedToPrepareUiForPlayback = false;


    public boolean isVideoSizeAvailable() {
        boolean isVideoSizeAvailable = mVideoSize.first != null && mVideoSize.second != null;
        Logger.v(TAG, "isVideoSizeAvailable " + isVideoSizeAvailable);
        return isVideoSizeAvailable;
    }

    public boolean isSurfaceTextureAvailable() {
        Logger.v(TAG, "isSurfaceTextureAvailable " + mSurfaceTextureAvailable);
        return mSurfaceTextureAvailable;
    }

    public boolean isReadyForPlayback() {
        boolean isReadyForPlayback = isVideoSizeAvailable() && isSurfaceTextureAvailable();
        Logger.v(TAG, "isReadyForPlayback " + isReadyForPlayback);
        return isReadyForPlayback;
    }

    public void setSurfaceTextureAvailable(boolean available) {
        mSurfaceTextureAvailable = available;
    }

    public void setVideoSize(Integer videoHeight, Integer videoWidth) {
        mVideoSize = new Pair<>(videoHeight, videoWidth);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + isReadyForPlayback();
    }

    public void setFailedToPrepareUiForPlayback(boolean failed) {
        mFailedToPrepareUiForPlayback = failed;
    }

    public boolean isFailedToPrepareUiForPlayback() {
        return mFailedToPrepareUiForPlayback;
    }
}
