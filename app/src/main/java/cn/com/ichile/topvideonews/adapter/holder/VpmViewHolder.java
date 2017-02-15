package cn.com.ichile.topvideonews.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import cn.com.ichile.topvideonews.R;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by SHCL on 2017/2/14.
 */

public class VpmViewHolder extends RecyclerView.ViewHolder {
    public final TextView tv_item_title_vpm;
    public final VideoPlayerView vpv;
    public final ImageView iv_pre_vpm;
    public final ImageView btn_play_vpm;
    public final TextView tv_item_author_vpm;
    public final ImageButton ib_item_share_vpm;
    public final ImageButton ib_item_like_vpm;

    public VpmViewHolder(View view) {
        super(view);
        tv_item_title_vpm = (TextView) view.findViewById(R.id.tv_item_title_vpm);
        vpv = (VideoPlayerView) view.findViewById(R.id.vpm);
        iv_pre_vpm = (ImageView) view.findViewById(R.id.iv_pre_vpm);
        btn_play_vpm = (ImageView) view.findViewById(R.id.btn_play_vpm);
        tv_item_author_vpm = (TextView) view.findViewById(R.id.tv_item_author_vpm);
        ib_item_share_vpm = (ImageButton) view.findViewById(R.id.ib_item_share_vpm);
        ib_item_like_vpm = (ImageButton) view.findViewById(R.id.ib_item_like_vpm);
    }
}
