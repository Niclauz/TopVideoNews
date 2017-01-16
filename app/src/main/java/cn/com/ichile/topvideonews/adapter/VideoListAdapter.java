package cn.com.ichile.topvideonews.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.activity.FullVideoActivity;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.domain.VideoBean;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 21:48.
 */

public class VideoListAdapter extends BaseAdapter implements OnNetDataCallback {

    private boolean isPlaying;
    private int indexPosition;
    private Activity mActivity;
    private List<VideoBean> mVideoBeanList;
    private LayoutInflater mInflater;
    private VideoBean videoBean;
    private VideoSuperPlayer outVideoSuperPlayer;

    public VideoListAdapter(Activity context, List<VideoBean> videoBeanList) {
        mActivity = context;
        mVideoBeanList = videoBeanList;
        mInflater = LayoutInflater.from(mActivity);
    }

    public int getIndexPosition() {
        return indexPosition;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public VideoSuperPlayer getPlayer() {
        return outVideoSuperPlayer;
    }

    public void setData(List<VideoBean> videoBeanList) {
        if (mVideoBeanList == null) {
            mVideoBeanList = new ArrayList<>();
        }
        mVideoBeanList.clear();
        mVideoBeanList.addAll(videoBeanList);
        myNotify();
    }


    private void myNotify() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
                // notifyDataSetInvalidated();
            }
        });
    }

    @Override
    public void onSuccess(Object... data) {
        setData((List<VideoBean>) data[0]);
        Log.i("videoListAdapter", "onsuccess");
    }

    @Override
    public void onError(Object... error) {

    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public int getCount() {
        return mVideoBeanList == null ? 0 : mVideoBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoBeanList == null ? null : mVideoBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        videoBean = mVideoBeanList.get(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_video, null, true);

            viewHolder.mIb_item_like = (ImageButton) convertView.findViewById(R.id.ib_item_like);
            viewHolder.mIb_item_share = (ImageButton) convertView.findViewById(R.id.ib_item_share);
            viewHolder.mIv_video_pre = (ImageView) convertView.findViewById(R.id.iv_video_pre);
            viewHolder.mPlay_btn = (ImageView) convertView.findViewById(R.id.play_btn);
            viewHolder.mTv_item_author = (TextView) convertView.findViewById(R.id.tv_item_author);
            viewHolder.mTv_item_title = (TextView) convertView.findViewById(R.id.tv_item_title);
            viewHolder.mVideoSuperPlayer = (VideoSuperPlayer) convertView.findViewById(R.id.video);
            // viewHolder.mVideoSuperPlayer.setVisibility(View.GONE);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        outVideoSuperPlayer = viewHolder.mVideoSuperPlayer;

        Picasso.with(mActivity)
                .load(videoBean.getImage())
                .fit()
                .placeholder(R.drawable.bg)
                .into(viewHolder.mIv_video_pre);

        viewHolder.mTv_item_title.setText(videoBean.getTitle());
        viewHolder.mTv_item_author.setText(videoBean.getAuthor());

        if (indexPosition == position&&isPlaying) {
            viewHolder.mVideoSuperPlayer.setVisibility(View.VISIBLE);

        } else {
            viewHolder.mVideoSuperPlayer.setVisibility(View.GONE);
            viewHolder.mVideoSuperPlayer.close();
        }


        viewHolder.mPlay_btn.setOnClickListener(new CusOnClick(viewHolder.mVideoSuperPlayer, viewHolder.mPlay_btn, position));

        return convertView;
    }


    public class ViewHolder {
        public VideoSuperPlayer mVideoSuperPlayer;
        TextView mTv_item_title, mTv_item_author;
        ImageView mIv_video_pre, mPlay_btn;
        ImageButton mIb_item_share, mIb_item_like;
    }

    class CusOnClick implements View.OnClickListener {
        VideoSuperPlayer mVideoSuperPlayer;
        ImageView mBtnPlay;
        int position;

        CusOnClick(VideoSuperPlayer mVideoSuperPlayer, ImageView mBtnPlay, int position) {
            this.mVideoSuperPlayer = mVideoSuperPlayer;
            this.mBtnPlay = mBtnPlay;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            MediaHelp.release();
            indexPosition = position;
            isPlaying = true;
            videoBean = mVideoBeanList.get(position);

            mVideoSuperPlayer.setVisibility(View.VISIBLE);
            mVideoSuperPlayer.loadAndPlay(MediaHelp.getInstance(), videoBean.getUrl(), 0, false);
            mVideoSuperPlayer.setVideoPlayCallback(new CusVideoPlayCallback(mActivity, mVideoSuperPlayer, mBtnPlay, videoBean));
            myNotify();
        }

    }

    class CusVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackInterface {
        private Activity mActivity;
        private VideoSuperPlayer mVideoSuperPlayer;
        private ImageView mBtn_play;
        private VideoBean mVideoBean;

        CusVideoPlayCallback(Activity activity, VideoSuperPlayer videoSuperPlayer, ImageView btn_play, VideoBean videoBean) {
            mActivity = activity;
            mVideoSuperPlayer = videoSuperPlayer;
            mVideoBean = videoBean;
            mBtn_play = btn_play;
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
            isPlaying = false;
            indexPosition = -1;
            mVideoSuperPlayer.close();
            mBtn_play.setVisibility(View.VISIBLE);
            mVideoSuperPlayer.setVisibility(View.GONE);
        }
    }


}
