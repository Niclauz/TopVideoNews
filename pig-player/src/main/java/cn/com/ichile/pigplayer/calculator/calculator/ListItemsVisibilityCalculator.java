package cn.com.ichile.pigplayer.calculator.calculator;


import cn.com.ichile.pigplayer.calculator.scroll_utils.ItemsPositionGetter;

/**
 * This is basic interface for Visibility calculator.
 * Methods of it strongly depends on Scroll events from ListView or RecyclerView
 */
public interface ListItemsVisibilityCalculator {
    void onScrollStateIdle(ItemsPositionGetter itemsPositionGetter, int firstVisiblePosition, int lastVisiblePosition);

    void onScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem, int visibleItemCount, int scrollState);
}
