package cn.com.ichile.topvideonews.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.com.ichile.topvideonews.App;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.domain.VideoBean;
import cn.com.ichile.topvideonews.widget.ListVideoPlayCallback;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.PlayStateCallback;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/9 - 14:03.
 */

public class RecommendRecyAdapter extends BaseRecycleAdapter<VideoBean> implements OnNetDataCallback {
    private Activity mActivity;
    private int indexPosition;
    private boolean isPlaying;
    private VideoBean mVideoBean;
    private VideoSuperPlayer mVideoSuperPlayer;
    private VideoSuperPlayer currPlayPlayer;

    public RecommendRecyAdapter(Activity activity, List list) {
        super(list);
        mActivity = activity;
    }

    public VideoSuperPlayer getVideoSuperPlayer() {
        return mVideoSuperPlayer;
    }

    public VideoSuperPlayer getCurrPlayPlayer() {
        return currPlayPlayer;
    }

    public int getIndexPosition() {
        return indexPosition;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    public void onSuccess(Object... data) {
        if (data != null) {
            setData((List<VideoBean>) data[0]);
        }
    }

    @Override
    public void onError(Object... error) {
        Toast.makeText(App.getAppContext(), ((Exception) error[0]).getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_video);
        return new VideoItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoItemHolder) {
            setItemValues((VideoItemHolder) holder, position);
        }
    }

    private void setItemValues(VideoItemHolder holder, int position) {
        mVideoBean = mDataList.get(position);
        Log.i("hhhhh", "hhhhh----" + mVideoBean.toString() + "");
        mVideoSuperPlayer = holder.mVideoSuperPlayer;

        holder.mTv_item_author.setText(mVideoBean.getAuthor());
        holder.mTv_item_title.setText(mVideoBean.getTitle());
        Picasso.with(App.getAppContext())
                .load(mVideoBean.getImage())
                .fit()
                .placeholder(R.drawable.bg)
                .into(holder.mIv_video_pre);

        if (indexPosition == position && isPlaying()) {
            holder.mVideoSuperPlayer.setVisibility(View.VISIBLE);
        } else {
            holder.mVideoSuperPlayer.setVisibility(View.GONE);
            holder.mVideoSuperPlayer.close();
        }


        holder.mPlay_btn.setOnClickListener(new RecyOnClick(holder.mVideoSuperPlayer, holder.mPlay_btn, position));

    }

    class RecyOnClick implements View.OnClickListener {
        VideoSuperPlayer cVideoSuperPlayer;
        ImageView mBtnPlay;
        int mPosition;

        RecyOnClick(VideoSuperPlayer videoSuperPlayer, ImageView btnPlay, int position) {
            cVideoSuperPlayer = videoSuperPlayer;
            mBtnPlay = btnPlay;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            MediaHelp.release();
            if (currPlayPlayer != null) {
                currPlayPlayer.close();
                currPlayPlayer.setVisibility(View.GONE);
            }
            currPlayPlayer = cVideoSuperPlayer;

            indexPosition = mPosition;
            isPlaying = true;
            mVideoBean = mDataList.get(indexPosition);
            cVideoSuperPlayer.setVisibility(View.VISIBLE);
            cVideoSuperPlayer.loadAndPlay(MediaHelp.getInstance(), mVideoBean.getUrl(), 0, false);
            cVideoSuperPlayer.setVideoPlayCallback(new ListVideoPlayCallback(mActivity, cVideoSuperPlayer, mBtnPlay, mVideoBean, new PlayStateCallback() {
                @Override
                public void onIndexPosition(int p) {
                    indexPosition = p;
                }

                @Override
                public void onPlayState(boolean p) {
                    isPlaying = p;
                }
            }));

            notifyDataSetChanged();
        }


    }


    class VideoItemHolder extends RecyclerView.ViewHolder {
        VideoSuperPlayer mVideoSuperPlayer;
        TextView mTv_item_title, mTv_item_author;
        ImageView mIv_video_pre, mPlay_btn;
        ImageButton mIb_item_share, mIb_item_like;

        public VideoItemHolder(View itemView) {
            super(itemView);
            mIb_item_like = (ImageButton) itemView.findViewById(R.id.ib_item_like);
            mIb_item_share = (ImageButton) itemView.findViewById(R.id.ib_item_share);
            mIv_video_pre = (ImageView) itemView.findViewById(R.id.iv_video_pre);
            mPlay_btn = (ImageView) itemView.findViewById(R.id.play_btn);
            mTv_item_author = (TextView) itemView.findViewById(R.id.tv_item_author);
            mTv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            mVideoSuperPlayer = (VideoSuperPlayer) itemView.findViewById(R.id.video);
        }
    }
}
