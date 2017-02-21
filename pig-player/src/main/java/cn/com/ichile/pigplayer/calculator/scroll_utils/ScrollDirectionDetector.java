package cn.com.ichile.pigplayer.calculator.scroll_utils;

import android.view.View;

import cn.com.ichile.pigplayer.utils.Logger;


/**
 * This class detects a {@link ScrollDirection} ListView is scrolled to.
 * And then call {@link OnDetectScrollListener#onScrollDirectionChanged(ScrollDirection)}
 *
 * @author danylo.volokh
 */
public class ScrollDirectionDetector {

    private static final String TAG = ScrollDirectionDetector.class.getSimpleName();

    private final OnDetectScrollListener mOnDetectScrollListener;

    private int mOldTop;
    private int mOldFirstVisibleItem;

    private ScrollDirection mOldScrollDirection = null;

    public ScrollDirectionDetector(OnDetectScrollListener onDetectScrollListener) {
        mOnDetectScrollListener = onDetectScrollListener;
    }

    public interface OnDetectScrollListener {
        void onScrollDirectionChanged(ScrollDirection scrollDirection);
    }

    public enum ScrollDirection {
        UP, DOWN
    }

    public void onDetectedListScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem) {
        Logger.v(TAG, ">> onDetectedListScroll, firstVisibleItem " + firstVisibleItem + ", mOldFirstVisibleItem " + mOldFirstVisibleItem);

        View view = itemsPositionGetter.getChildAt(0);
        int top = (view == null) ? 0 : view.getTop();
        Logger.v(TAG, "onDetectedListScroll, view " + view + ", top " + top + ", mOldTop " + mOldTop);

        if (firstVisibleItem == mOldFirstVisibleItem) {
            if (top > mOldTop) {
                onScrollUp();
            } else if (top < mOldTop) {
                onScrollDown();
            }
        } else {
            if (firstVisibleItem < mOldFirstVisibleItem) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }

        mOldTop = top;
        mOldFirstVisibleItem = firstVisibleItem;
        Logger.v(TAG, "<< onDetectedListScroll");
    }

    private void onScrollDown() {
        Logger.v(TAG, "onScroll Down");

        if (mOldScrollDirection != ScrollDirection.DOWN) {
            mOldScrollDirection = ScrollDirection.DOWN;
            mOnDetectScrollListener.onScrollDirectionChanged(ScrollDirection.DOWN);
        } else {
            Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + mOldScrollDirection);
        }
    }

    private void onScrollUp() {
        Logger.v(TAG, "onScroll Up");

        if (mOldScrollDirection != ScrollDirection.UP) {
            mOldScrollDirection = ScrollDirection.UP;
            mOnDetectScrollListener.onScrollDirectionChanged(ScrollDirection.UP);
        } else {
            Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + mOldScrollDirection);
        }
    }
}
