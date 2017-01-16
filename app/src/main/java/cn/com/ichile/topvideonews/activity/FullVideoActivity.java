package cn.com.ichile.topvideonews.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.domain.VideoBean;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.VideoMediaController;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/3 - 16:27.
 */

public class FullVideoActivity extends BaseActivity {

    private VideoSuperPlayer mVideoFull;
    private VideoBean mVideoBean;

    @Override
    public void baseOnCreate(@Nullable Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full);

        mVideoFull = (VideoSuperPlayer) findViewById(R.id.video_full);
        mVideoBean = (VideoBean) getIntent().getExtras().getSerializable("video");
        mVideoFull.loadAndPlay(MediaHelp.getInstance(), mVideoBean.getUrl(), getIntent().getExtras().getInt("position"), true);
        mVideoFull.setPageType(VideoMediaController.PageType.EXPAND);
        mVideoFull.setVideoPlayCallback(new VideoSuperPlayer.VideoPlayCallbackInterface() {

            @Override
            public void onCloseVideo() {
                goBack();
            }

            @Override
            public void onSwitchPageType() {
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    goBack();
                }
            }

            @Override
            public void onPlayFinish() {
                goBack();
            }
        });
    }

    private void goBack() {
        Intent i = new Intent();
        i.putExtra("position", mVideoFull.getCurrentPosition());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaHelp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaHelp.resume();
    }
}
