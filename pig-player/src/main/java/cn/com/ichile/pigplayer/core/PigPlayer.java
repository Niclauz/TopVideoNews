package cn.com.ichile.pigplayer.core;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.ichile.pigplayer.core.ui.MediaPlayerWrapper;
import cn.com.ichile.pigplayer.core.ui.MediaPlayerWrapperImpl;
import cn.com.ichile.pigplayer.core.ui.ReadyForPlaybackIndicator;
import cn.com.ichile.pigplayer.core.ui.ScalableTextureView;
import cn.com.ichile.pigplayer.utils.HandlerThreadExtension;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/14.
 * <p>
 * https://github.com/danylovolokh/VideoPlayerManager
 */

public class PigPlayer extends ScalableTextureView
        implements TextureView.SurfaceTextureListener,
        MediaPlayerWrapper.MainThreadMediaPlayerListener,
        MediaPlayerWrapper.VideoStateListener {
    private static final String TAG = "PigPlayer";
    private static final String IS_VIDEO_MUTED = "IS_VIDEO_MUTED";

    /**
     * MediaPlayer instance
     * <p>
     * MediaPlayerWrapper instance.
     * you should call it from background thread to avoid ANR
     */
    private MediaPlayerWrapper mMediaPlayer;

    private HandlerThreadExtension mViewHandlerBackgroundThread;

    /**
     * A Listener that propagates {@link MediaPlayer} listeners is background thread.
     * Probably call of this listener should also need to be synchronized with it creation and destroy places.
     */
    private BackgroundThreadMediaPlayerListener mMediaPlayerListenerBackgroundThread;

    private MediaPlayerWrapper.VideoStateListener mVideoStateListener;
    private SurfaceTextureListener mLocalSurfaceTextureListener;

    private AssetFileDescriptor mAssetFileDescriptor;
    private String mPath;

    private final ReadyForPlaybackIndicator mReadyForPlaybackIndicator = new ReadyForPlaybackIndicator();

    private final Set<MediaPlayerWrapper.MainThreadMediaPlayerListener> mMediaPlayerMainThreadListeners = new HashSet<>();

    public MediaPlayerWrapper.State getCurrentState() {
        synchronized (mReadyForPlaybackIndicator) {
            return mMediaPlayer.getCurrentState();
        }
    }

    public AssetFileDescriptor getAssetFileDescriptorDataSource() {
        return mAssetFileDescriptor;
    }

    public String getVideoUrlDataSource() {
        return mPath;
    }


//    /**
//     * Creates a new TextureView.
//     *
//     * @param context      The context to associate this view with.
//     * @param attrs        The attributes of the XML tag that is inflating the view.
//     * @param defStyleAttr An attribute in the current theme that contains a
//     *                     reference to a style resource that supplies default values for
//     *                     the view. Can be 0 to not look for defaults.
//     * @param defStyleRes  A resource identifier of a style resource that
//     *                     supplies default values for the view, used only if
//     */
//    public PigPlayer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initView();
//    }

    /**
     * Creates a new TextureView.
     *
     * @param context      The context to associate this view with.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     */
    public PigPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public PigPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * Creates a new TextureView.
     *
     * @param context The context to associate this view with.
     */
    public PigPlayer(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if (!isInEditMode()) {
            Logger.v(TAG, "initView");

            setScaleType(ScalableTextureView.ScaleType.CENTER_CROP);
            super.setSurfaceTextureListener(this);
        }

    }

    public void reset() {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.reset();
        }
    }

    public void release() {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.release();
        }
    }

    public void clearPlayerInstance() {
        Logger.v(TAG, ">> clearPlayerInstance");

        checkThread();

        synchronized (mReadyForPlaybackIndicator) {
            mReadyForPlaybackIndicator.setVideoSize(null, null);
            mMediaPlayer.clearAll();
            mMediaPlayer = null;
        }

        Logger.v(TAG, "<< clearPlayerInstance");
    }

    public void createNewPlayerInstance() {
        Logger.v(TAG, ">> createNewPlayerInstance");

        Logger.v(TAG, "createNewPlayerInstance main Looper " + Looper.getMainLooper());
        Logger.v(TAG, "createNewPlayerInstance my Looper " + Looper.myLooper());

        checkThread();
        synchronized (mReadyForPlaybackIndicator) {

            mMediaPlayer = new MediaPlayerWrapperImpl();

            mReadyForPlaybackIndicator.setVideoSize(null, null);
            mReadyForPlaybackIndicator.setFailedToPrepareUiForPlayback(false);

            if (mReadyForPlaybackIndicator.isSurfaceTextureAvailable()) {
                SurfaceTexture texture = getSurfaceTexture();
                Logger.v(TAG, "texture " + texture);
                mMediaPlayer.setSurfaceTexture(texture);
            } else {
                Logger.v(TAG, "texture not available");
            }
            mMediaPlayer.setMainThreadMediaPlayerListener(this);
            mMediaPlayer.setVideoStateListener(this);
        }
        Logger.v(TAG, "<< createNewPlayerInstance");
    }

    public void prepare() {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.prepare();
        }
    }

    public void stop() {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.stop();
        }
    }

    public void start() {
        Logger.v(TAG, ">> start");
        synchronized (mReadyForPlaybackIndicator) {
            if (mReadyForPlaybackIndicator.isReadyForPlayback()) {
                mMediaPlayer.start();
            } else {
                Logger.v(TAG, "start, >> wait");
                if (!mReadyForPlaybackIndicator.isFailedToPrepareUiForPlayback()) {
                    try {
                        mReadyForPlaybackIndicator.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    Logger.v(TAG, "start, << wait");

                    if (mReadyForPlaybackIndicator.isReadyForPlayback()) {
                        mMediaPlayer.start();
                    } else {
                        Logger.w(TAG, "start, movie is not ready, Player become STARTED state, but it will actually don't play");
                    }
                } else {
                    Logger.w(TAG, "start, movie is not ready. Video size will not become available");
                }
            }
        }
        Logger.v(TAG, "<< start");
    }

    public void pause() {
        Logger.d(TAG, ">> pause ");
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.pause();
        }
        Logger.d(TAG, "<< pause");
    }

    public void setDataSource(String path) {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {

            Logger.v(TAG, "setDataSource, path " + path + ", this " + this);

            try {
                mMediaPlayer.setDataSource(path);

            } catch (IOException e) {
                Logger.d(TAG, e.getMessage());
                throw new RuntimeException(e);
            }
            mPath = path;
        }
    }

    public void setDataSource(AssetFileDescriptor assetFileDescriptor) {
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {


            Logger.v(TAG, "setDataSource, assetFileDescriptor " + assetFileDescriptor + ", this " + this);

            try {
                mMediaPlayer.setDataSource(assetFileDescriptor);
            } catch (IOException e) {
                Logger.d(TAG, e.getMessage());
                throw new RuntimeException(e);
            }
            mAssetFileDescriptor = assetFileDescriptor;
        }
    }

    public int getDuration() {
        synchronized (mReadyForPlaybackIndicator) {
            return mMediaPlayer.getDuration();
        }
    }

    public void setOnVideoStateChangedListener(MediaPlayerWrapper.VideoStateListener listener) {
        mVideoStateListener = listener;
        checkThread();
        synchronized (mReadyForPlaybackIndicator) {
            mMediaPlayer.setVideoStateListener(listener);
        }
    }

    public void addMediaPlayerListener(MediaPlayerWrapper.MainThreadMediaPlayerListener listener) {
        synchronized (mMediaPlayerMainThreadListeners) {
            mMediaPlayerMainThreadListeners.add(listener);
        }
    }

    public void setBackgroundThreadMediaPlayerListener(BackgroundThreadMediaPlayerListener listener) {
        mMediaPlayerListenerBackgroundThread = listener;
    }

    private void notifyOnVideoStopped() {
        Logger.v(TAG, "notifyOnVideoStopped");
        List<MediaPlayerWrapper.MainThreadMediaPlayerListener> listCopy;
        synchronized (mMediaPlayerMainThreadListeners) {
            listCopy = new ArrayList<>(mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener listener : listCopy) {
            listener.onVideoStoppedMainThread();
        }
    }

    private boolean isVideoSizeAvailable() {
        boolean isVideoSizeAvailable = getContentHeight() != null && getContentWidth() != null;
        Logger.v(TAG, "isVideoSizeAvailable " + isVideoSizeAvailable);
        return isVideoSizeAvailable;
    }


    /**
     * Sets the TextureView.SurfaceTextureListener used to listen to surface texture events.
     *
     * @param listener
     */
    @Override
    public final void setSurfaceTextureListener(SurfaceTextureListener listener) {
        mLocalSurfaceTextureListener = listener;
    }

    /**
     * Invoked when a {@link TextureView}'s SurfaceTexture is ready for use.
     *
     * @param surfaceTexture The surface returned by
     *                       {@link TextureView#getSurfaceTexture()}
     * @param width          The width of the surface
     * @param height         The height of the surface
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Logger.v(TAG, "onSurfaceTextureAvailable, width " + width + ", height " + height + ", this " + this);
        if (mLocalSurfaceTextureListener != null) {
            mLocalSurfaceTextureListener.onSurfaceTextureAvailable(surfaceTexture, width, height);
        }
        notifyTextureAvailable();
    }

    private void notifyTextureAvailable() {
        Logger.v(TAG, ">> notifyTextureAvailable");

        mViewHandlerBackgroundThread.post(new Runnable() {
            @Override
            public void run() {
                Logger.v(TAG, ">> run notifyTextureAvailable");

                synchronized (mReadyForPlaybackIndicator) {

                    if (mMediaPlayer != null) {
                        mMediaPlayer.setSurfaceTexture(getSurfaceTexture());
                    } else {
                        mReadyForPlaybackIndicator.setVideoSize(null, null);

                        Logger.v(TAG, "mMediaPlayer null, cannot set surface texture");
                    }
                    mReadyForPlaybackIndicator.setSurfaceTextureAvailable(true);

                    if (mReadyForPlaybackIndicator.isReadyForPlayback()) {
                        Logger.v(TAG, "notify ready for playback");
                        mReadyForPlaybackIndicator.notifyAll();
                    }
                }
                Logger.v(TAG, "<< run notifyTextureAvailable");
            }
        });
        Logger.v(TAG, "<< notifyTextureAvailable");
    }

    /**
     * Invoked when the {@link SurfaceTexture}'s buffers size changed.
     *
     * @param surface The surface returned by
     *                {@link TextureView#getSurfaceTexture()}
     * @param width   The new width of the surface
     * @param height  The new height of the surface
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mLocalSurfaceTextureListener != null) {
            mLocalSurfaceTextureListener.onSurfaceTextureSizeChanged(surface, width, height);
        }
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is about to be destroyed.
     * If returns true, no rendering should happen inside the surface texture after this method
     * is invoked. If returns false, the client needs to call {@link SurfaceTexture#release()}.
     * Most applications should return true.
     *
     * @param surface The surface about to be destroyed
     */
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Logger.v(TAG, "onSurfaceTextureDestroyed, surface " + surface);

        if (mLocalSurfaceTextureListener != null) {
            mLocalSurfaceTextureListener.onSurfaceTextureDestroyed(surface);
        }

        if (isAttachedToWindow()) {
            mViewHandlerBackgroundThread.post(new Runnable() {
                @Override
                public void run() {

                    synchronized (mReadyForPlaybackIndicator) {
                        mReadyForPlaybackIndicator.setSurfaceTextureAvailable(false);

                        /** we have to notify a Thread may be in wait() state in {@link VideoPlayerView#start()} method*/
                        mReadyForPlaybackIndicator.notifyAll();
                    }
                }
            });
        }

        // We have to release this surface manually for better control.
        // Also we do this because we return false from this method
        surface.release();
        return false;
    }

    /**
     * Invoked when the specified {@link SurfaceTexture} is updated through
     * {@link SurfaceTexture#updateTexImage()}.
     *
     * @param surface The surface just updated
     */
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mLocalSurfaceTextureListener != null) {
            mLocalSurfaceTextureListener.onSurfaceTextureUpdated(surface);
        }
    }

    @Override
    public void onVideoSizeChangedMainThread(int width, int height) {
        Logger.v(TAG, ">> onVideoSizeChangedMainThread, width " + width + ", height " + height);

        if (width != 0 && height != 0) {
            setContentWidth(width);
            setContentHeight(height);

            onVideoSizeAvailable();
        } else {
            Logger.w(TAG, "onVideoSizeChangedMainThread, size 0. Probably will be unable to start video");

            synchronized (mReadyForPlaybackIndicator) {
                mReadyForPlaybackIndicator.setFailedToPrepareUiForPlayback(true);
                mReadyForPlaybackIndicator.notifyAll();
            }
        }

        notifyOnVideoSizeChangedMainThread(width, height);

        Logger.v(TAG, "<< onVideoSizeChangedMainThread, width " + width + ", height " + height);
    }

    @Override
    public void onVideoPreparedMainThread() {
        notifyOnVideoPreparedMainThread();

        if (mMediaPlayerListenerBackgroundThread != null) {
            mViewHandlerBackgroundThread.post(mVideoPreparedBackgroundThreadRunnable);
        }
    }

    @Override
    public void onVideoCompletionMainThread() {
        notifyOnVideoCompletionMainThread();
        if (mMediaPlayerListenerBackgroundThread != null) {
            mViewHandlerBackgroundThread.post(mVideoCompletionBackgroundThreadRunnable);
        }
    }

    @Override
    public void onErrorMainThread(final int what, final int extra) {
        Logger.v(TAG, "onErrorMainThread, this " + PigPlayer.this);
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Logger.v(TAG, "onErrorMainThread, what MEDIA_ERROR_SERVER_DIED");
                printErrorExtra(extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Logger.v(TAG, "onErrorMainThread, what MEDIA_ERROR_UNKNOWN");
                printErrorExtra(extra);
                break;
        }

        notifyOnErrorMainThread(what, extra);

        if (mMediaPlayerListenerBackgroundThread != null) {
            mViewHandlerBackgroundThread.post(new Runnable() {
                @Override
                public void run() {
                    mMediaPlayerListenerBackgroundThread.onErrorBackgroundThread(what, extra);
                }
            });
        }
    }

    @Override
    public void onBufferingUpdateMainThread(int percent) {

    }

    @Override
    public void onVideoStoppedMainThread() {
        notifyOnVideoStopped();
    }

    @Override
    public void onVideoPlayTimeChanged(int positionInMilliseconds) {

    }

    public void muteVideo() {
        synchronized (mReadyForPlaybackIndicator) {
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(IS_VIDEO_MUTED, true).commit();
            mMediaPlayer.setVolume(0, 0);
        }
    }

    public void unMuteVideo() {
        synchronized (mReadyForPlaybackIndicator) {
            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean(IS_VIDEO_MUTED, false).commit();
            mMediaPlayer.setVolume(1, 1);
        }
    }

    public boolean isAllVideoMute() {
        return PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(IS_VIDEO_MUTED, false);
    }


    private void onVideoSizeAvailable() {
        Logger.v(TAG, ">> onVideoSizeAvailable");

        updateTextureViewSize();

        if (isAttachedToWindow()) {
            mViewHandlerBackgroundThread.post(mVideoSizeAvailableRunnable);
        }

        Logger.v(TAG, "<< onVideoSizeAvailable");
    }

    @Override
    public boolean isAttachedToWindow() {
        return mViewHandlerBackgroundThread != null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean isInEditMode = isInEditMode();
        Logger.v(TAG, ">> onAttachedToWindow " + isInEditMode);
        if (!isInEditMode) {
            mViewHandlerBackgroundThread = new HandlerThreadExtension(TAG, false);
            mViewHandlerBackgroundThread.startThread();
        }

        Logger.v(TAG, "<< onAttachedToWindow");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        boolean isInEditMode = isInEditMode();

        Logger.v(TAG, ">> onDetachedFromWindow, isInEditMode " + isInEditMode);
        if (!isInEditMode) {
            mViewHandlerBackgroundThread.postQuit();
            mViewHandlerBackgroundThread = null;
        }
        Logger.v(TAG, "<< onDetachedFromWindow");
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        boolean isInEditMode = isInEditMode();

        Logger.v(TAG, ">> onVisibilityChanged " + visibilityStr(visibility) + ", isInEditMode " + isInEditMode);
        if (!isInEditMode) {

            switch (visibility) {
                case VISIBLE:
                    break;
                case INVISIBLE:
                case GONE:
                    synchronized (mReadyForPlaybackIndicator) {
                        // have to notify worker thread in case we exited this screen without getting ready for playback
                        mReadyForPlaybackIndicator.notifyAll();
                    }
            }
        }

        Logger.v(TAG, "<< onVisibilityChanged");
    }

    private static String visibilityStr(int visibility) {
        switch (visibility) {
            case VISIBLE:
                return "VISIBLE";
            case INVISIBLE:
                return "INVISIBLE";
            case GONE:
                return "GONE";
            default:
                throw new RuntimeException("unexpected");
        }
    }

    private void notifyOnVideoSizeChangedMainThread(int width, int height) {
        Logger.v(TAG, "notifyOnVideoSizeChangedMainThread, width " + width + ", height " + height);
        List<MediaPlayerWrapper.MainThreadMediaPlayerListener> listCopy;
        synchronized (mMediaPlayerMainThreadListeners) {
            listCopy = new ArrayList<>(mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener listener : listCopy) {
            listener.onVideoSizeChangedMainThread(width, height);
        }
    }

    private void notifyOnVideoCompletionMainThread() {
        Logger.v(TAG, "notifyVideoCompletionMainThread");
        List<MediaPlayerWrapper.MainThreadMediaPlayerListener> listCopy;
        synchronized (mMediaPlayerMainThreadListeners) {
            listCopy = new ArrayList<>(mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener listener : listCopy) {
            listener.onVideoCompletionMainThread();
        }
    }

    private void notifyOnVideoPreparedMainThread() {
        Logger.v(TAG, "notifyOnVideoPreparedMainThread");
        List<MediaPlayerWrapper.MainThreadMediaPlayerListener> listCopy;
        synchronized (mMediaPlayerMainThreadListeners) {
            listCopy = new ArrayList<>(mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener listener : listCopy) {
            listener.onVideoPreparedMainThread();
        }
    }

    private void notifyOnErrorMainThread(int what, int extra) {
        Logger.v(TAG, "notifyOnErrorMainThread");
        List<MediaPlayerWrapper.MainThreadMediaPlayerListener> listCopy;
        synchronized (mMediaPlayerMainThreadListeners) {
            listCopy = new ArrayList<>(mMediaPlayerMainThreadListeners);
        }
        for (MediaPlayerWrapper.MainThreadMediaPlayerListener listener : listCopy) {
            listener.onErrorMainThread(what, extra);
        }
    }

    private final Runnable mVideoSizeAvailableRunnable = new Runnable() {
        @Override
        public void run() {
            Logger.v(TAG, ">> run, onVideoSizeAvailable");

            synchronized (mReadyForPlaybackIndicator) {

                Logger.v(TAG, "onVideoSizeAvailable, mReadyForPlaybackIndicator " + mReadyForPlaybackIndicator);

                mReadyForPlaybackIndicator.setVideoSize(getContentHeight(), getContentWidth());

                if (mReadyForPlaybackIndicator.isReadyForPlayback()) {
                    Logger.v(TAG, "run, onVideoSizeAvailable, notifyAll");

                    mReadyForPlaybackIndicator.notifyAll();
                }
                Logger.v(TAG, "<< run, onVideoSizeAvailable");
            }
            if (mMediaPlayerListenerBackgroundThread != null) {
                mMediaPlayerListenerBackgroundThread.onVideoSizeChangedBackgroundThread(getContentHeight(), getContentWidth());
            }
        }
    };

    private final Runnable mVideoCompletionBackgroundThreadRunnable = new Runnable() {
        @Override
        public void run() {
            mMediaPlayerListenerBackgroundThread.onVideoSizeChangedBackgroundThread(getContentHeight(), getContentWidth());
        }
    };

    private final Runnable mVideoPreparedBackgroundThreadRunnable = new Runnable() {
        @Override
        public void run() {
            mMediaPlayerListenerBackgroundThread.onVideoPreparedBackgroundThread();
        }
    };

    private void checkThread() {
//        if (Looper.myLooper() == Looper.getMainLooper()) {
//            throw new RuntimeException("cannot be in main thread");
//        }
    }


    public interface BackgroundThreadMediaPlayerListener {
        void onVideoSizeChangedBackgroundThread(int width, int height);

        void onVideoPreparedBackgroundThread();

        void onVideoCompletionBackgroundThread();

        void onErrorBackgroundThread(int what, int extra);
    }

    private void printErrorExtra(int extra) {
        switch (extra) {
            case MediaPlayer.MEDIA_ERROR_IO:
                Logger.v(TAG, "error extra MEDIA_ERROR_IO");
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                Logger.v(TAG, "error extra MEDIA_ERROR_MALFORMED");
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                Logger.v(TAG, "error extra MEDIA_ERROR_UNSUPPORTED");
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                Logger.v(TAG, "error extra MEDIA_ERROR_TIMED_OUT");
                break;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode();
    }
}
