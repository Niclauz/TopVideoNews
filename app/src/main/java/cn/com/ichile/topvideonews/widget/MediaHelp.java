package cn.com.ichile.topvideonews.widget;

import android.media.MediaPlayer;


/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 20:48.
 * Original From GitHub.
 */
public class MediaHelp {
    private static MediaPlayer mPlayer;

    public static MediaPlayer getInstance() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        return mPlayer;
    }

    /**
     * MediaPlayer resume
     */
    public static void resume() {
        try {
            if (mPlayer != null) {
                mPlayer.start();
            }
        } catch (Exception e) {

        }
    }

    /**
     * MediaPlayer pause
     */
    public static void pause() {
        try {
            if (mPlayer != null) {
                mPlayer.pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * MediaPlayer release
     */
    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
