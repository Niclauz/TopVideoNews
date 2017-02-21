package cn.com.ichile.pigplayer.core.messages;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.FileDescriptor;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.manager.PlayerManagerCallback;

/**
 * This PlayerMessage calls {@link MediaPlayer#setDataSource(FileDescriptor)} on the instance that is used inside {@link cn.com.ichile.pigplayer.core.PigPlayer}
 */
public class SetAssetsDataSourceMessage extends SetDataSourceMessage {

    private final AssetFileDescriptor mAssetFileDescriptor;

    public SetAssetsDataSourceMessage(PigPlayer videoPlayerView, AssetFileDescriptor assetFileDescriptor, PlayerManagerCallback callback) {
        super(videoPlayerView, callback);
        mAssetFileDescriptor = assetFileDescriptor;
    }

    @Override
    protected void performAction(PigPlayer currentPlayer) {
        currentPlayer.setDataSource(mAssetFileDescriptor);
    }
}
