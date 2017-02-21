package cn.com.ichile.pigplayer.core.ui;

import android.media.MediaPlayer;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/15.
 *
 * https://github.com/danylovolokh/VideoPlayerManager
 */

public class MediaPlayerWrapperImpl extends MediaPlayerWrapper {
    public MediaPlayerWrapperImpl() {
        super(new MediaPlayer());
    }
}
