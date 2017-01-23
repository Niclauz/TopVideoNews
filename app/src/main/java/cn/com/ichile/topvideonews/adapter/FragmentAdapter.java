package cn.com.ichile.topvideonews.adapter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.api.message.SectionQueryResponse;
import cn.api.model.Section;
import cn.com.ichile.topvideonews.callback.OnFragmentBind;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.fragment.RecommendFragment;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 21:20.
 */

public class FragmentAdapter extends FragmentPagerAdapter implements OnNetDataCallback {
    private List<Fragment> mFragmentList;
    private List<Section> mTitleList;
    private TabLayout mTabLayout;
    private Fragment mCurrentFragment;
    private OnFragmentBind mOnFragmentBind;

    public FragmentAdapter(FragmentManager fm, TabLayout tabLayout,OnFragmentBind onFragmentBind) {
        super(fm);
        this.mTabLayout = tabLayout;
        this.mOnFragmentBind = onFragmentBind;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position % mTitleList.size()).getName();
        } else {
            return null;
        }
    }


    @Override
    public void onSuccess(Object... data) {
        if (data[0] instanceof SectionQueryResponse) {
            SectionQueryResponse responses = (SectionQueryResponse) data[0];
            mTitleList = responses.getSectionList();
            if (mFragmentList == null) {
                mFragmentList = new ArrayList<>();
            }

            for (int i = 0; i < mTitleList.size(); i++) {
                RecommendFragment recommendFragment = new RecommendFragment();
                Bundle args = new Bundle();
                args.putSerializable("section", mTitleList.get(i));
                recommendFragment.setArguments(args);
                mFragmentList.add(recommendFragment);
            }


            if (mTitleList.size() > 5) {
                mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            } else {
                mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                mTabLayout.setTabMode(TabLayout.MODE_FIXED);
            }
            mTabLayout.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
            mOnFragmentBind.onBind(mCurrentFragment);
        }
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
