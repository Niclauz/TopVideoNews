package cn.com.ichile.topvideonews.widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageView;

import cn.com.ichile.topvideonews.activity.FullVideoActivity;
import cn.com.ichile.topvideonews.domain.VideoBean;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/11 - 14:20.
 */

public class ListVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackInterface {
    private Activity mActivity;
    private VideoSuperPlayer mVideoSuperPlayer;
    private ImageView mBtn_play;
    private VideoBean mVideoBean;
    private PlayStateCallback mPlayStateCallback;

    public ListVideoPlayCallback(Activity activity, VideoSuperPlayer videoSuperPlayer, ImageView btn_play, VideoBean videoBean, PlayStateCallback playStateCallback) {
        mActivity = activity;
        mVideoSuperPlayer = videoSuperPlayer;
        mVideoBean = videoBean;
        mBtn_play = btn_play;
        mPlayStateCallback = playStateCallback;
    }

    @Override
    public void onCloseVideo() {
        closeVideo();
    }

    @Override
    public void onSwitchPageType() {
        if (((Activity) mActivity).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(new Intent(mActivity, FullVideoActivity.class));
            intent.putExtra("video", mVideoBean);
            intent.putExtra("position", mVideoSuperPlayer.getCurrentPosition());
            ((Activity) mActivity).startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onPlayFinish() {
        closeVideo();
    }

    public void closeVideo() {
        mPlayStateCallback.onPlayState(false);
        mPlayStateCallback.onIndexPosition(-1);
        mVideoSuperPlayer.close();
        mBtn_play.setVisibility(View.VISIBLE);
        mVideoSuperPlayer.setVisibility(View.GONE);
    }
}
