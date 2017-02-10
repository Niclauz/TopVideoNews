package cn.com.ichile.topvideonews.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.tools.utils.ResHelper;
import com.mob.tools.utils.UIHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.api.message.ContentListOfSectionQueryResponse;
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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.onekeyshare.ShareTool;
import cn.sharesdk.socialization.Comment;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentListener;
import cn.sharesdk.socialization.LikeListener;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.component.ReplyTooFrequentlyException;


/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/9 - 14:03.
 */

public class RecommendRecyAdapter extends BaseRecycleAdapter<Content> implements OnNetDataCallback, View.OnClickListener, Handler.Callback {
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
    // 模拟的主题id
    private String topicId;
    // 模拟的主题标题
    private String topicTitle;
    // 模拟的主题发布时间
    private String topicPublishTime;
    // 模拟的主题作者
    private String topicAuthor;
    private OnekeyShare oks;
    private QuickCommentBar qcBar;
    private CommentFilter filter;
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
        if (data != null && data[0] instanceof ContentListOfSectionQueryResponse) {
            ContentListOfSectionQueryResponse csqResponse = (ContentListOfSectionQueryResponse) data[0];
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
        if (data != null && data[0] instanceof ContentListOfSectionQueryResponse) {
            ContentListOfSectionQueryResponse csqResponse = (ContentListOfSectionQueryResponse) data[0];
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

    private void setItemValues(VideoItemHolder holder, int position) {

        Content c = mDataList.get(position);
        if (Constants.ComposeType.OnlyMain.equals(c.getComposeType())) {
            mContentMain = c.getMainContent();
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
        holder.mIb_item_share.setOnClickListener(this);
    }

    private void initShareSdk() {
        ShareSDK.initSDK(mActivity);
        ShareSDK.registerService(Socialization.class);

        new Thread() {
            public void run() {
                UIHandler.sendEmptyMessageDelayed(INIT_SDK, 100, RecommendRecyAdapter.this);
            }
        }.start();

        //设置评论监听
        Socialization.setCommentListener(new CommentListener() {

            @Override
            public void onSuccess(Comment comment) {
                int resId = ResHelper.getStringRes(mActivity, "ssdk_socialization_reply_succeeded");
                if (resId > 0) {
                    Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail(Comment comment) {
                Toast.makeText(mActivity, comment.getFileCodeString(mActivity), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {
                if (throwable instanceof ReplyTooFrequentlyException) {
                    int resId = ResHelper.getStringRes(mActivity, "ssdk_socialization_replay_too_frequently");
                    if (resId > 0) {
                        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    throwable.printStackTrace();
                }
            }
        });

        Socialization.setLikeListener(new LikeListener() {

            @Override
            public void onSuccess(String topicId, String topicTitle, String commentId) {
                Message msg = new Message();
                msg.what = AFTER_LIKE;
                msg.arg1 = 1;
                UIHandler.sendMessage(msg, RecommendRecyAdapter.this);
            }

            @Override
            public void onFail(String topicId, String topicTitle, String commentId, String error) {
                Message msg = new Message();
                msg.what = AFTER_LIKE;
                msg.arg1 = 2;
                UIHandler.sendMessage(msg, RecommendRecyAdapter.this);
            }

        });
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case INIT_SDK:
                topicId = mActivity.getString(R.string.comment_like_id);
                topicTitle = mActivity.getString(R.string.comment_like_title);
                topicPublishTime = mActivity.getString(R.string.comment_like_publich_time);
                topicAuthor = mActivity.getString(R.string.comment_like_author);

                //TopicTitle tt = (TopicTitle) findViewById(R.id.llTopicTitle);
//                String topicTitle = mActivity.getString(R.string.comment_like_title);
//                tt.setTitle(topicTitle);
//                tt.setPublishTime(mActivity.getString(R.string.comment_like_publich_time));
//                tt.setAuthor(mActivity.getString(R.string.comment_like_author));


                Socialization service = ShareSDK.getService(Socialization.class);
                //service.setCustomPlatform(new MyPlatform(this));
                initOnekeyShare();
                break;
            case AFTER_LIKE:
                if (msg.arg1 == 1) {
                    //success
                    int resId = ResHelper.getStringRes(mActivity, "like_success");
                    if (resId > 0) {
                        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //fail
                    int resId = ResHelper.getStringRes(mActivity, "like_fail");
                    if (resId > 0) {
                        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 3:
                break;
            default:
                break;

        }

        return false;
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
                    Logger.i(TAG,"ccccccccc");
                    Object tag = v.getTag();
                    ContentMain contentMain = null;
                    if (tag != null && tag instanceof ContentMain) {
                        contentMain  = (ContentMain) tag;
                        if (contentMain != null) {
                            StoreUtil.addCollection(App.getAppContext(),contentMain,contentMain.getId());
                            UiUtil.showSimpleSnackbar(App.getAppContext(),v,"收藏成功" + contentMain.getId(),null,null);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    UiUtil.showSimpleSnackbar(App.getAppContext(),v,"收藏失败，请稍后重试！",null,null);
                }
                break;
            case R.id.ib_item_share:
                Object tag = v.getTag();
                ContentMain contentMain = null;
                if (tag != null && tag instanceof ContentMain) {
                    contentMain  = (ContentMain) tag;
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

            notifyDataSetChanged();
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
