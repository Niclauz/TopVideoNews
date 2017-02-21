package cn.com.ichile.pigplayer.calculator.calculator;

import android.view.View;

import cn.com.ichile.pigplayer.calculator.items.ListItem;
import cn.com.ichile.pigplayer.utils.Logger;

/**
 * Default implementation. You can override it and intercept switching between active items
 * <p>
 * Created by danylo.volokh on 05.01.2016.
 */
public class DefaultSingleItemCalculatorCallback implements SingleListViewItemActiveCalculator.Callback<ListItem> {

    private static final String TAG = DefaultSingleItemCalculatorCallback.class.getSimpleName();

    @Override
    public void activateNewCurrentItem(ListItem newListItem, View newView, int newViewPosition) {

        Logger.v(TAG, "activateNewCurrentItem, newListItem " + newListItem);
        Logger.v(TAG, "activateNewCurrentItem, newViewPosition " + newViewPosition);

        /**
         * Here you can do whatever you need with a newly "active" ListItem.
         */
        newListItem.setActive(newView, newViewPosition);
    }

    @Override
    public void deactivateCurrentItem(ListItem listItemToDeactivate, View view, int position) {
        Logger.v(TAG, "deactivateCurrentItem, listItemToDeactivate " + listItemToDeactivate);
        /**
         * When view need to stop being active we call deactivate.
         */
        listItemToDeactivate.deactivate(view, position);
    }
}
