package cn.com.ichile.topvideonews.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.com.ichile.topvideonews.callback.OnNetDataCallback;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 21:08.
 */

public class ViewPagerAdapter extends PagerAdapter implements OnNetDataCallback{
    private Context mContext;
    private List<View> mViewList;
    private List<String> mTitleList;

    public ViewPagerAdapter(Context context, List<View> viewList, List<String> titleList) {
        this.mContext = context;
        this.mViewList = viewList;
        this.mTitleList = titleList;
    }

    @Override
    public int getCount() {
        return mViewList == null ? 0 : mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(mViewList.get(position), 0);
        return mViewList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(mViewList.get(position
        ));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);
    }

    @Override
    public void onSuccess(Object... data) {

    }

    @Override
    public void onMore(Object... data) {

    }

    @Override
    public void onError(Object... error) {

    }

    @Override
    public void onProgress(int progress) {

    }
}
