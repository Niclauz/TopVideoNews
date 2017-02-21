package cn.com.ichile.topvideonews.adapter;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.api.message.ContentListByTypeQueryResponse;
import cn.api.model.Constants;
import cn.api.model.Content;
import cn.api.model.ContentMain;
import cn.com.ichile.topvideonews.App;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.util.Logger;
import cn.com.ichile.topvideonews.util.StoreUtil;
import cn.com.ichile.topvideonews.util.UiUtil;
import cn.com.ichile.topvideonews.widget.ListVideoPlayCallback;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.PlayStateCallback;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.onekeyshare.ShareTool;


/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/9 - 14:03.
 */

public class RecommendRecyAdapter extends BaseRecycleAdapter<Content> implements OnNetDataCallback, View.OnClickListener{
    private static final int INIT_SDK = 1;
    private static final int AFTER_LIKE = 2;
    private static final String TAG = "RecommendRecyAdapter";
    private Activity mActivity;
    private int indexPosition;
    private boolean isPlaying;
    private ContentMain mContentMain;
    private VideoSuperPlayer currPlayPlayer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //share sdk
    private OnekeyShare oks;
    private TextView mTv_footer_loading;
    private ListVideoPlayCallback videoPlayCallback;

    public RecommendRecyAdapter(Activity activity, List list, SwipeRefreshLayout swipeRefreshLayout) {
        super(list);
        mActivity = activity;
        this.mSwipeRefreshLayout = swipeRefreshLayout;
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
        if (data != null && data[0] instanceof ContentListByTypeQueryResponse) {
            ContentListByTypeQueryResponse csqResponse = (ContentListByTypeQueryResponse) data[0];
            List<Content> contentList = csqResponse.getContentList();
            addAll(contentList);
        }
        changeRefresh(false);
    }

    private void changeRefresh(final boolean isRefresh) {
        if (mSwipeRefreshLayout != null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(isRefresh);
                }
            });

        }
    }

    @Override
    public void onMore(Object... data) {
        if (data != null && data[0] instanceof ContentListByTypeQueryResponse) {
            ContentListByTypeQueryResponse csqResponse = (ContentListByTypeQueryResponse) data[0];
            List<Content> contentList = csqResponse.getContentList();
            if (mTv_footer_loading != null && mTv_footer_loading.getVisibility() == View.VISIBLE) {
                if (contentList.size() == 0) {
                    mTv_footer_loading.setText("暂时没有更多，刷新试试！");
                    mTv_footer_loading.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //mSwipeRefreshLayout.
                        }
                    });
                } else {
                    mTv_footer_loading.setText("加载中...");
                }
            }
            addMore(contentList);
        }
        changeRefresh(false);
    }

    @Override
    public void onError(Object... error) {
        //Toast.makeText(App.getAppContext(), ((Exception) error[0]).getMessage(), Toast.LENGTH_SHORT).show();
        changeRefresh(false);
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 1 : (mDataList.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) == getItemCount()) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM) {
            View view = getView(parent, R.layout.item_video);
            VideoItemHolder videoItemHolder = new VideoItemHolder(view);
            //****************init share sdk
            // qcBar = videoItemHolder.mQuickCommentBar;
            //initShareSdk();
            return videoItemHolder;
        } else if (viewType == FOOTER) {
            View view = getView(parent, R.layout.item_footer);
            ViewFooterHolder viewFooterHolder = new ViewFooterHolder(view);
            mTv_footer_loading = viewFooterHolder.mTv_footer_loading;
            return viewFooterHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoItemHolder) {
            setItemValues((VideoItemHolder) holder, position);
        }
    }



    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Logger.i(TAG,"**********************************");
        if(holder instanceof  VideoItemHolder){
            VideoItemHolder holdera=  (VideoItemHolder)holder;
            if(holdera.mVideoSuperPlayer!=null&&isPlaying){
                holdera.mVideoSuperPlayer.close();
                holdera.mVideoSuperPlayer.setVisibility(View.GONE);
                isPlaying = false;
            }
        }
    }

    private void setItemValues(VideoItemHolder holder, int position) {

        Content c = mDataList.get(position);
        if (Constants.ComposeType.OnlyMain.equals(c.getComposeType())) {
            mContentMain = c.getMainContent();
        }

        if (mContentMain == null) {
            return;
        }

        Log.i("hhhhh", "hhhhh----" + mContentMain.toString() + "");
        currPlayPlayer = holder.mVideoSuperPlayer;


        holder.mTv_item_author.setText(mContentMain.getSrcSite());
        holder.mTv_item_title.setText(mContentMain.getTitle1());
        Picasso.with(App.getAppContext())
                .load(mContentMain.getImage1())
                .fit()
                .placeholder(R.drawable.bg)
                .error(R.drawable.bg)
                .into(holder.mIv_video_pre);
        if (indexPosition == position && isPlaying) {
            holder.mVideoSuperPlayer.setVisibility(View.VISIBLE);
        } else {
            holder.mVideoSuperPlayer.setVisibility(View.GONE);
            holder.mVideoSuperPlayer.close();
        }


        holder.mPlay_btn.setOnClickListener(new RecyOnClick(holder.mVideoSuperPlayer, holder.mPlay_btn, position));

        Logger.i(TAG, "CONTENT ID--" + mContentMain.getId());
        holder.mIb_item_like.setOnClickListener(this);
        holder.mIb_item_like.setTag(mContentMain);
        if (StoreUtil.isCollected(App.getAppContext(), mContentMain)) {
            holder.mIb_item_like.setImageDrawable(App.getAppContext().getResources().getDrawable(R.drawable.like_selected));
        } else {
            holder.mIb_item_like.setImageDrawable(App.getAppContext().getResources().getDrawable(R.drawable.like_normal));
        }
        holder.mIb_item_share.setOnClickListener(this);
    }



    // Socialization服务依赖OnekeyShare组件，此方法初始化一个OnekeyShare对象
    // 此方法的代码从DemoPage中复制而来
    private void initOnekeyShare() {
        oks = new OnekeyShare();
        oks.setAddress("12345678901");
        oks.setTitle(mActivity.getString(R.string.ssdk_oks_share));
        oks.setTitleUrl("http://mob.com");
        oks.setText(mActivity.getString(R.string.share_content));
        oks.setUrl("http://www.mob.com");
        oks.setComment(mActivity.getString(R.string.ssdk_oks_share));
        oks.setSite(mActivity.getString(R.string.app_name));
        oks.setSiteUrl("http://mob.com");
        oks.setVenueName("ShareSDK");
        oks.setVenueDescription("This is a beautiful place!");
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                // 改写twitter分享内容中的text字段，否则会超长，
                // 因为twitter会将图片地址当作文本的一部分去计算长度
                if ("Twitter".equals(platform.getName())) {
                    paramsToShare.setText(platform.getContext().getString(R.string.share_content_short));
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_item_like:
                try {
                    Logger.i(TAG, "ccccccccc");
                    Object tag = v.getTag();
                    ContentMain contentMain = null;
                    if (tag != null && tag instanceof ContentMain) {
                        contentMain = (ContentMain) tag;
                        if (!StoreUtil.isCollected(App.getAppContext(), contentMain)) {
                            StoreUtil.addCollectionUnique(App.getAppContext(), contentMain);
                            ((ImageButton) v).setImageDrawable(App.getAppContext().getResources().getDrawable(R.drawable.like_selected));
                            UiUtil.showSimpleSnackbar(App.getAppContext(), v, "收藏成功" + contentMain.getId(), null, null);
                        } else {
                            StoreUtil.deleteCollectionUnique(App.getAppContext(), contentMain);
                            ((ImageButton) v).setImageDrawable(App.getAppContext().getResources().getDrawable(R.drawable.like_normal));
                            UiUtil.showSimpleSnackbar(App.getAppContext(), v, "取消收藏成功" + contentMain.getId(), null, null);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    UiUtil.showSimpleSnackbar(App.getAppContext(), v, "收藏失败，请稍后重试！", null, null);
                }
                break;
            case R.id.ib_item_share:
                Object tag = v.getTag();
                ContentMain contentMain = null;
                if (tag != null && tag instanceof ContentMain) {
                    contentMain = (ContentMain) tag;
                    if (contentMain != null) {
                        ShareTool shareTool = new ShareTool(App.getAppContext());
                        shareTool.showShare(contentMain.getSrcSite(), null, contentMain.getTitle1(), contentMain.getImage1(), contentMain.getPlayStreaming());
                    }
                }

            default:
                break;
        }

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

            if (Constants.ComposeType.OnlyMain.equals(mDataList.get(indexPosition).getComposeType())) {
                mContentMain = mDataList.get(indexPosition).getMainContent();
            }
            cVideoSuperPlayer.setVisibility(View.VISIBLE);
            cVideoSuperPlayer.loadAndPlay(MediaHelp.getInstance(), mContentMain.getPlayStreaming(), 0, false);
            videoPlayCallback = new ListVideoPlayCallback(mActivity, cVideoSuperPlayer, mBtnPlay, mContentMain, new PlayStateCallback() {
                @Override
                public void onIndexPosition(int p) {
                    indexPosition = p;
                }

                @Override
                public void onPlayState(boolean p) {
                    isPlaying = p;
                }
            });
            cVideoSuperPlayer.setVideoPlayCallback(videoPlayCallback);

            //notifyDataSetChanged();
        }


    }


    class VideoItemHolder extends RecyclerView.ViewHolder {
        VideoSuperPlayer mVideoSuperPlayer;
        TextView mTv_item_title, mTv_item_author;
        ImageView mIv_video_pre, mPlay_btn;
        ImageButton mIb_item_share, mIb_item_like;
        // QuickCommentBar mQuickCommentBar;

        public VideoItemHolder(View itemView) {
            super(itemView);
            mIb_item_like = (ImageButton) itemView.findViewById(R.id.ib_item_like);
            mIb_item_share = (ImageButton) itemView.findViewById(R.id.ib_item_share);
            mIv_video_pre = (ImageView) itemView.findViewById(R.id.iv_video_pre);
            mPlay_btn = (ImageView) itemView.findViewById(R.id.play_btn);
            mTv_item_author = (TextView) itemView.findViewById(R.id.tv_item_author);
            mTv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
            mVideoSuperPlayer = (VideoSuperPlayer) itemView.findViewById(R.id.video);
            // mQuickCommentBar = (QuickCommentBar) itemView.findViewById(R.id.item_qcb);
        }
    }

    class ViewFooterHolder extends RecyclerView.ViewHolder {
        TextView mTv_footer_loading;

        public ViewFooterHolder(View itemView) {
            super(itemView);
            mTv_footer_loading = (TextView) itemView.findViewById(R.id.tv_footer_loading);
        }
    }
}
