package cn.com.ichile.topvideonews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.activity.MainActivity;
import cn.com.ichile.topvideonews.adapter.VideoListAdapter;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.net.DataUtil;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 21:03.
 */

public class TvFragment extends BaseFragment {
    public SwipeRefreshLayout mSwipeRefresh;
    private ListView mLvTv;
    private VideoListAdapter mAdapter;
    private MainActivity activity;
    private VideoSuperPlayer videoSuperPlayer;
    private boolean isPrepared;

    @Override
    public View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void initView(View view) {
        mLvTv = (ListView) view.findViewById(R.id.lv_tv);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_tv);
        activity = (MainActivity) getActivity();
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorActionBar));
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisiable) {
            return;
        }
        DataUtil.getVideoList(Cons.TV, mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new VideoListAdapter(activity, null);
        mLvTv.setAdapter(mAdapter);

        mLvTv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    int indexPosition = ((VideoListAdapter) mLvTv.getAdapter()).getIndexPosition();
                    boolean isPlaying = ((VideoListAdapter) mLvTv.getAdapter()).getIsPlaying();
                    videoSuperPlayer = ((VideoListAdapter) mLvTv.getAdapter()).getPlayer();
                    if ((indexPosition < mLvTv.getFirstVisiblePosition()
                            || indexPosition > mLvTv.getLastVisiblePosition())
                            && isPlaying) {

                        if (videoSuperPlayer != null) {
                            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = videoSuperPlayer.getVideoPlayCallback();
                            if (videoPlayCallback != null) {
                                videoPlayCallback.onCloseVideo();
                                mAdapter.notifyDataSetChanged();
                                MediaHelp.release();
                            }

                        }

                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //int indexPosition = ((VideoListAdapter)mLvHot.getAdapter()).indexPosition;
                //boolean isPlaying = ((VideoListAdapter)mLvHot.getAdapter()).isPlaying;
//                if (indexPosition < firstVisibleItem
//                        || indexPosition > mLvHot.getLastVisiblePosition()
//                         ) {
////                        indexPosition = -1;
////                        isPlaying = false;
//                    mAdapter.notifyDataSetChanged();
//                    MediaHelp.release();
//                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoSuperPlayer != null) {
            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = videoSuperPlayer.getVideoPlayCallback();
            if (videoPlayCallback != null) videoPlayCallback.onCloseVideo();
        }
        mAdapter.notifyDataSetChanged();
        MediaHelp.release();
    }


    @Override
    public OnNetDataCallback getItemAdapter() {
        return mAdapter;
    }


}
