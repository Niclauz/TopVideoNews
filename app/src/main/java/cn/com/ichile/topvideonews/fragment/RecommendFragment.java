package cn.com.ichile.topvideonews.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.adapter.RecommendRecyAdapter;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.net.NetUtil;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/1/3 - 9:57.
 */

public class RecommendFragment extends BaseFragment {
    private SwipeRefreshLayout mSwipeRef;
    private RecyclerView mRecycleView;
    private RecommendRecyAdapter mRecommendRecyAdapter;
    private boolean isPrepared;
    private RecyclerView.OnScrollListener onScrollListener;

    @Nullable
    @Override
    public View inflateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void initView(View view) {
        initSwipeRef(view);
        initRecycleView(view);

    }

    @Override
    protected void lazyLoad() {
        if (!isVisiable || !isPrepared) {
            return;
        }
        //设置数据
        NetUtil.getVideoList(Cons.RECOMMEND, mRecommendRecyAdapter);

    }


    private void initRecycleView(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_recommend);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mRecycleView.addItemDecoration(new );
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mRecommendRecyAdapter = new RecommendRecyAdapter(getActivity(), null);
        mRecycleView.setAdapter(mRecommendRecyAdapter);
        mRecommendRecyAdapter.notifyDataSetChanged();
    }

    private void initSwipeRef(View view) {
        mSwipeRef = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recommend);
    }


    @Override
    public String setDataType() {
        return Cons.RECOMMEND;
    }

    @Override
    public OnNetDataCallback getItemAdapter() {
        return mRecommendRecyAdapter;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化首页数据
        NetUtil.getVideoList(Cons.RECOMMEND, mRecommendRecyAdapter);

        onScrollListener = new RecyclerView.OnScrollListener() {
            private int mLastVisibleItemPosition;
            private int mFirstVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecommendRecyAdapter adapter = (RecommendRecyAdapter) mRecycleView.getAdapter();
                    VideoSuperPlayer videoSuperPlayer = adapter.getVideoSuperPlayer();
                    int indexPosition = adapter.getIndexPosition();
                    boolean playing = adapter.isPlaying();
                    if ((indexPosition < mFirstVisibleItemPosition
                            || indexPosition > mLastVisibleItemPosition)
                            ) {
//                        if (videoSuperPlayer != null) {
//                            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = videoSuperPlayer.getVideoPlayCallback();
//                            if (videoPlayCallback != null) {
//                                videoPlayCallback.onCloseVideo();
//                                mRecommendRecyAdapter.notifyDataSetChanged();
//                                MediaHelp.release();
//                            }
//
//                        }

                        VideoSuperPlayer currPlayPlayer = adapter.getCurrPlayPlayer();
                        if (currPlayPlayer != null) {
                            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = currPlayPlayer.getVideoPlayCallback();
                            videoPlayCallback.onCloseVideo();
                            currPlayPlayer.setVisibility(View.GONE);
                        }


                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = mRecycleView.getLayoutManager();
                LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
                if (layoutManager instanceof LinearLayoutManager) {
                    mFirstVisibleItemPosition = lm.findFirstVisibleItemPosition();
                    mLastVisibleItemPosition = lm.findLastVisibleItemPosition();
                }
            }
        };
        mRecycleView.addOnScrollListener(onScrollListener);

    }

    @Override
    public void onDestroyView() {
        mRecycleView.removeOnScrollListener(onScrollListener);
        super.onDestroyView();
    }
}
