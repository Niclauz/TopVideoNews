package cn.com.ichile.pigplayer.item;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.ichile.pigplayer.R;
import cn.com.ichile.pigplayer.calculator.items.ListItem;
import cn.com.ichile.pigplayer.core.manager.PlayerManager;
import cn.com.ichile.pigplayer.core.manager.VideoItem;
import cn.com.ichile.pigplayer.core.meta.CurrentItemMeta;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.core.ui.MediaPlayerWrapper;
import cn.com.ichile.pigplayer.holder.VideoItemHolder;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/20.
 */

public abstract class BaseVideoItem implements ListItem, VideoItem {
    private static String TAG = "BaseVideoItem";
    private final PlayerManager<MetaData> mPlayerManager;
    //// TODO: 2017/2/20 better idea???
    private final Rect mCurrentRect = new Rect();

    protected BaseVideoItem(PlayerManager<MetaData> playerManager) {
        mPlayerManager = playerManager;
    }

    /**
     * call this in
     * {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     *
     * @param position
     * @param viewHolder
     * @param playerManager
     */
    public abstract void updateView(int position, VideoItemHolder viewHolder, PlayerManager playerManager);

    /**
     * When this method is called, the implementation should provide a visibility percents in range 0 - 100 %
     *
     * @param currentView the view which visibility percent should be calculated.
     *             Note: visibility doesn't have to depend on the visibility of a full view.
     *             It might be calculated by calculating the visibility of any inner View
     * @return percents of visibility
     */
    @Override
    public int getVisibilityPercents(View currentView) {
        Logger.v(TAG, ">> getVisibilityPercents currentView " + currentView);
        int percent = 100;

        currentView.getLocalVisibleRect(mCurrentRect);

        int height = currentView.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percent = (height - mCurrentRect.top) * 100 / height;
        }else if(viewIsPartiallyHiddenBottom(height)){
            percent = mCurrentRect.bottom * 100 / height;
        }

        setVisiabilityPercentText(currentView,percent);

        return percent;
    }

    private void setVisiabilityPercentText(View currentView,int percent) {
        VideoItemHolder videoItemHolder = (VideoItemHolder) currentView.getTag();
        videoItemHolder.tv_item_author.setText("percent--" + String.valueOf(percent));
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentRect.bottom > 0 && mCurrentRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentRect.top > 0;
    }

    /**
     * When view visibility become bigger than "current active" view visibility then the new view becomes active.
     * This method is called
     *
     * @param newActiveView
     * @param newActiveViewPosition
     */
    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        //remember to setTag in adapter
        VideoItemHolder holder = (VideoItemHolder) newActiveView.getTag();
        //start new play callback
        playNewVideo(new CurrentItemMeta(newActiveViewPosition, newActiveView), holder.video_player, mPlayerManager);
    }

    /**
     * There might be a case when not only new view becomes active, but also when no view is active.
     * When view should stop being active this method is called
     * <p>
     * stop play here
     *
     * @param currentView
     * @param position
     */
    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mPlayerManager);
    }

    public View createView(ViewGroup parent, int screenWidth) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = screenWidth;

        final VideoItemHolder videoItemHolder = new VideoItemHolder(view);
        view.setTag(videoItemHolder);

        videoItemHolder.video_player.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
            @Override
            public void onVideoSizeChangedMainThread(int width, int height) {

            }

            @Override
            public void onVideoPreparedMainThread() {
                //ready to play , hide cover image
                videoItemHolder.iv_item_video_pre.setVisibility(View.GONE);
            }

            @Override
            public void onVideoCompletionMainThread() {

            }

            @Override
            public void onErrorMainThread(int what, int extra) {

            }

            @Override
            public void onBufferingUpdateMainThread(int percent) {

            }

            @Override
            public void onVideoStoppedMainThread() {
                //show cover
                videoItemHolder.iv_item_video_pre.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }



}
