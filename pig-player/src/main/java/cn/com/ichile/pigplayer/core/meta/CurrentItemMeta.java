package cn.com.ichile.pigplayer.core.meta;

import android.view.View;

/**
 * FBI WARNING * MAGIC * DO NOT TOUCH *
 * Created by Nick Wong on 2017/2/16.
 */

public class CurrentItemMeta implements MetaData {
    public int currentItemPosition;
    public View currentItemView;

    public CurrentItemMeta(int currentItemPosition, View currentItemView) {
        this.currentItemPosition = currentItemPosition;
        this.currentItemView = currentItemView;
    }
}
