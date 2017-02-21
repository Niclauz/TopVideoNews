package cn.com.ichile.pigplayer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.ichile.pigplayer.R;
import cn.com.ichile.pigplayer.core.PigPlayer;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/20.
 */

public class VideoItemHolder extends RecyclerView.ViewHolder {
    public  TextView tv_item_title;
    public  ImageView iv_item_video_pre;
    public  ImageView btn_item_play;
    public  PigPlayer video_player;
    public  TextView tv_item_author;
    public  ImageButton ib_item_share;
    public  ImageButton ib_item_like;

    public VideoItemHolder(View itemView) {
        super(itemView);
        tv_item_title = (TextView) itemView.findViewById(R.id.tv_item_title);
        iv_item_video_pre = (ImageView) itemView.findViewById(R.id.iv_item_video_pre);
        btn_item_play = (ImageView) itemView.findViewById(R.id.btn_item_play);
        video_player = (PigPlayer) itemView.findViewById(R.id.video_player);
        tv_item_author = (TextView) itemView.findViewById(R.id.tv_item_author);
        ib_item_share = (ImageButton) itemView.findViewById(R.id.ib_item_share);
        ib_item_like = (ImageButton) itemView.findViewById(R.id.ib_item_like);
    }
}
