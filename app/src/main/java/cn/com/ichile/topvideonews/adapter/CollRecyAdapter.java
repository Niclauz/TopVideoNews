package cn.com.ichile.topvideonews.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.api.message.ContentListPreciseQueryResponse;
import cn.api.model.Constants;
import cn.api.model.Content;
import cn.api.model.ContentMain;
import cn.com.ichile.topvideonews.App;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.widget.ListVideoPlayCallback;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.PlayStateCallback;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/9.
 */

public class CollRecyAdapter extends BaseRecycleAdapter<ContentMain> implements OnNetDataCallback, View.OnClickListener {

    private ContentMain mContentMain;
    private VideoSuperPlayer currPlayPlayer;
    private int indexPosition;
    private boolean isPlaying;
    private Activity mActivity;

    public CollRecyAdapter(List list, Activity activity) {
        super(list);
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(Object... data) {
        if (data != null && data[0] instanceof ContentListPreciseQueryResponse) {
            ContentListPreciseQueryResponse response = (ContentListPreciseQueryResponse) data[0];
            List<Content> contentList = response.getContentList();
            ArrayList<ContentMain> contentMains = new ArrayList<>();
            for (Content c : contentList) {
                if (Constants.ComposeType.OnlyMain.equals(c.getComposeType())) {
                    ContentMain contentMain = c.getMainContent();
                    contentMains.add(contentMain);
                }
            }
            addAll(contentMains);
        }

    }

    @Override
    public void onMore(Object... data) {

    }

    @Override
    public void onError(Object... error) {

    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList == null ? 0 : mDataList.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent, R.layout.item_video);
        ViewItemHolder viewItemHolder = new ViewItemHolder(view);
        return viewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewItemHolder viewItemHolder = (ViewItemHolder) holder;
        mContentMain = mDataList.get(position);
        viewItemHolder.mTv_item_author.setText(mContentMain.getSrcSite());
        viewItemHolder.mTv_item_title.setText(mContentMain.getTitle1());
        Picasso.with(App.getAppContext())
                .load(mContentMain.getImage1())
                .fit()
                .placeholder(R.drawable.bg)
                .error(R.drawable.bg)
                .into(viewItemHolder.mIv_video_pre);
        if (indexPosition == position && isPlaying) {
            viewItemHolder.mVideoSuperPlayer.setVisibility(View.VISIBLE);
        } else {
            viewItemHolder.mVideoSuperPlayer.setVisibility(View.GONE);
            viewItemHolder.mVideoSuperPlayer.close();
        }


        viewItemHolder.mPlay_btn.setOnClickListener(new RecyOnClick(viewItemHolder.mVideoSuperPlayer, viewItemHolder.mPlay_btn, position));
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

            cVideoSuperPlayer.setVisibility(View.VISIBLE);
            cVideoSuperPlayer.loadAndPlay(MediaHelp.getInstance(), mContentMain.getPlayStreaming(), 0, false);
            cVideoSuperPlayer.setVideoPlayCallback(new ListVideoPlayCallback(mActivity, cVideoSuperPlayer, mBtnPlay, mContentMain, new PlayStateCallback() {
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

    class ViewItemHolder extends RecyclerView.ViewHolder {

        VideoSuperPlayer mVideoSuperPlayer;
        TextView mTv_item_title, mTv_item_author;
        ImageView mIv_video_pre, mPlay_btn;
        ImageButton mIb_item_share, mIb_item_like;

        public ViewItemHolder(View itemView) {
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
