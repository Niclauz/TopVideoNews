package cn.com.ichile.pigplayer.core.messages;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage calls {@link MediaPlayer#setDataSource(Context, Uri)} on the instance that is used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class SetUrlDataSourceMessage extends SetDataSourceMessage {

    private final String mVideoUrl;

    public SetUrlDataSourceMessage(PigPlayer videoPlayerView, String videoUrl, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
        mVideoUrl = videoUrl;
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.setDataSource(mVideoUrl);
    }
}
