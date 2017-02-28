package cn.com.ichile.pigplayer.item;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.com.ichile.pigplayer.calculator.items.ListItem;
import cn.com.ichile.pigplayer.core.manager.PlayerManager;
import cn.com.ichile.pigplayer.core.manager.VideoItem;
import cn.com.ichile.pigplayer.core.meta.CurrentItemMeta;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.holder.VideoItemHolder;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/20.
 */

public abstract class BaseVideoItem implements ListItem,VideoItem {
    private static String TAG = "BaseVideoItem";
    private final PlayerManager<MetaData> mPlayerManager;
    //// TODO: 2017/2/20 better idea???
    private final Rect mCurrentRect = new Rect();
    protected BaseVideoItem(PlayerManager<MetaData> playerManager) {
        mPlayerManager = playerManager;
    }

    /**
     * call this in
     *  {@link android.support.v7.widget.RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     * @param position
     * @param viewHolder
     * @param playerManager
     */
    public abstract void updateView(int position, VideoItemHolder viewHolder, PlayerManager playerManager);

    /**
     * When this method is called, the implementation should provide a visibility percents in range 0 - 100 %
     *
     * @param view the view which visibility percent should be calculated.
     *             Note: visibility doesn't have to depend on the visibility of a full view.
     *             It might be calculated by calculating the visibility of any inner View
     * @return percents of visibility
     */
    @Override
    public int getVisibilityPercents(View view) {
        return 0;
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
        //remember setTag in adapter
        VideoItemHolder holder = (VideoItemHolder) newActiveView.getTag();
        //start new play callback
        playNewVideo(new CurrentItemMeta(newActiveViewPosition,newActiveView),holder.video_player,mPlayerManager);
    }

    /**
     * There might be a case when not only new view becomes active, but also when no view is active.
     * When view should stop being active this method is called
     *
     * stop play here
     * @param currentView
     * @param position
     */
    @Override
    public void deactivate(View currentView, int position) {
        stopPlayback(mPlayerManager);
    }

   // public View createView(ViewGroup parent,int )

}
