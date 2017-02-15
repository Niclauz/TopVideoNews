package cn.com.ichile.topvideonews.adapter.items;

import android.graphics.Rect;
import android.view.View;

import com.volokh.danylo.video_player_manager.manager.VideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.items.ListItem;

import cn.com.ichile.topvideonews.adapter.holder.VpmViewHolder;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/14.
 */

public abstract class BaseVideoItem implements VideoItem, ListItem {

    private final Rect mCurrentViewRect = new Rect();
    private final VideoPlayerManager<MetaData> mVideoPlayerManager;

    BaseVideoItem(VideoPlayerManager<MetaData> videoPlayerManager) {
        mVideoPlayerManager = videoPlayerManager;
    }

    public abstract void update(int position, VpmViewHolder viewHolder, VideoPlayerManager videoPlayerManager);

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        VpmViewHolder viewHolder = (VpmViewHolder) newActiveView.getTag();
        //auto play new visiable item
        playNewVideo(new CurrentItemMetaData(newActiveViewPosition, newActiveView), viewHolder.vpv, mVideoPlayerManager);
    }

    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mVideoPlayerManager);
    }


}
