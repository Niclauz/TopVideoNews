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
 * Created by WangZQ on 2016/12/28 - 20:48.
 */

public class HotFragment extends BaseFragment {


    public SwipeRefreshLayout mSwipeRefresh;
    private ListView mLvHot;
    private VideoListAdapter mAdapter;
    private MainActivity activity;
    private VideoSuperPlayer videoSuperPlayer;
    private boolean isPrepared;

    @Nullable
    @Override
    public View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        activity = (MainActivity) getActivity();
        isPrepared = true;
        lazyLoad();
        return view;
    }


    @Override
    public OnNetDataCallback getItemAdapter() {
        return mAdapter;
    }

    @Override
    public void initView(View view) {
        mLvHot = (ListView) view.findViewById(R.id.lv_hot);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_hot);
        mSwipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorActionBar));

        mAdapter = new VideoListAdapter(activity, null);
        mLvHot.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {
        if (!isVisiable || !isPrepared) {
            return;
        }
        DataUtil.getVideoList(Cons.HOT,mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mLvHot.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) {
                    int indexPosition = ((VideoListAdapter) mLvHot.getAdapter()).getIndexPosition();
                    boolean isPlaying = ((VideoListAdapter) mLvHot.getAdapter()).getIsPlaying();
                    videoSuperPlayer = ((VideoListAdapter) mLvHot.getAdapter()).getPlayer();
                    if ((indexPosition < mLvHot.getFirstVisiblePosition()
                            || indexPosition > mLvHot.getLastVisiblePosition())
                            && isPlaying) {

                        if (videoSuperPlayer != null) {
                            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = videoSuperPlayer.getVideoPlayCallback();
                            if (videoPlayCallback != null) videoPlayCallback.onCloseVideo();
                        }
                        mAdapter.notifyDataSetChanged();
                        MediaHelp.release();
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
    public void onPause() {
        super.onPause();
        if (videoSuperPlayer != null) {
            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = videoSuperPlayer.getVideoPlayCallback();
            if (videoPlayCallback != null) videoPlayCallback.onCloseVideo();
        }
        mAdapter.notifyDataSetChanged();
        MediaHelp.release();
    }

}
