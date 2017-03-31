package cn.com.ichile.pigplayer.item;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.com.ichile.pigplayer.R;
import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.manager.PlayerManager;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.holder.VideoItemHolder;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/3/14 - 16:22.
 */

public class NetVideoItem extends BaseVideoItem {
    private String mTitle;
    private String mAuthor;
    private String mUrl;
    private String mCoverImg;
    private Activity mActivity;

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getCoverImg() {
        return mCoverImg;
    }

    public Activity getActivity() {
        return mActivity;
    }

    protected NetVideoItem(Activity activity, PlayerManager<MetaData> playerManager,
                           String title, String coverImg, String author, String url) {
        super(playerManager);
        mActivity = activity;
        mTitle = title;
        mAuthor = author;
        mUrl = url;
        mCoverImg = coverImg;
    }

    @Override
    public void updateView(int position, VideoItemHolder viewHolder, PlayerManager playerManager) {
        viewHolder.tv_item_author.setText(mAuthor);
        viewHolder.tv_item_title.setText(mTitle);
        Glide.with(mActivity)
                .load(mCoverImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.bg)
                .placeholder(R.drawable.bg)
                .into(viewHolder.iv_item_video_pre);

    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, PigPlayer player,
                             PlayerManager<MetaData> playerManager) {
        playerManager.startNewPlay(currentItemMetaData, player, mUrl);
    }

    @Override
    public void stopPlayback(PlayerManager playerManager) {
        playerManager.stopPlayCallback();
    }
}
