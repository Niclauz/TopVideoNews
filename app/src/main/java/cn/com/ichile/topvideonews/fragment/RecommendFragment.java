package cn.com.ichile.topvideonews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.activity.MainActivity;
import cn.com.ichile.topvideonews.adapter.RecommendRecyAdapter;
import cn.com.ichile.topvideonews.callback.OnNetDataCallback;
import cn.com.ichile.topvideonews.net.DataUtil;
import cn.com.ichile.topvideonews.util.UiUtil;
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


    public RecyclerView getRecycleView() {
        return mRecycleView;
    }

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
        if (!isVisiable || !isPrepared || mRecommendRecyAdapter == null) {
            return;
        }
        //设置数据
        // DataUtil.getVideoList(Cons.RECOMMEND, mRecommendRecyAdapter);
        DataUtil.getSectionList(mRecommendRecyAdapter, productCode, sectionId);
    }

    long startId = 1;

    private void initRecycleView(View view) {
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_recommend);
        mRecycleView.setHasFixedSize(true);
        final WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
//        mRecycleView.addItemDecoration(new );
        mRecommendRecyAdapter = new RecommendRecyAdapter(getActivity(), null, mSwipeRef);
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int mLastVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == recyclerView.SCROLL_STATE_IDLE
                        && mLastVisibleItemPosition + 1 == mRecommendRecyAdapter.getItemCount()) {
                    //***************load more
                    int size = mRecommendRecyAdapter.getData().size();
                    //long startId = size <= 1 ? 0 : mRecommendRecyAdapter.getData().get(mRecommendRecyAdapter.getData().size() - 1).getMainContent().getId();

                    DataUtil.getMoreSectionList(mRecommendRecyAdapter, productCode, sectionId, ++startId);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });

        mRecycleView.setAdapter(mRecommendRecyAdapter);
    }

    private void initSwipeRef(final View view) {
        mSwipeRef = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_recommend);
        mSwipeRef.setColorSchemeColors(R.color.colorActionBar);
        mSwipeRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Snackbar snackbar = Snackbar.make(view, "已刷新", Snackbar.LENGTH_SHORT)
//                        .setAction("确定", null);
//                snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorActionBar));
//                View snackbarView = snackbar.getView();
//                TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
//                Button button = (Button) snackbarView.findViewById(R.id.snackbar_action);
//                tv.setGravity(Gravity.CENTER);
//                tv.setTextSize(18);
//                button.setTextColor(getResources().getColor(R.color.colorWhite));
//                snackbar.show();


                UiUtil.showSimpleSnackbar(getContext(), view, "已刷新", "确定", null);
                DataUtil.getSectionList(mRecommendRecyAdapter, productCode, sectionId);
            }
        });
    }


    @Override
    public OnNetDataCallback getItemAdapter() {
        return mRecommendRecyAdapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();

        //初始化首页数据
        if (mainActivity.getPagerPosition() == 0) {
            //DataUtil.getVideoList(Cons.RECOMMEND, mRecommendRecyAdapter);
            DataUtil.getSectionList(mRecommendRecyAdapter, productCode, sectionId);
        }

        onScrollListener = new RecyclerView.OnScrollListener() {
            private int mLastVisibleItemPosition;
            private int mFirstVisibleItemPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecommendRecyAdapter adapter = (RecommendRecyAdapter) mRecycleView.getAdapter();
                    int indexPosition = adapter.getIndexPosition();
//                    VideoSuperPlayer videoSuperPlayer = adapter.getVideoSuperPlayer();
//                    boolean playing = adapter.isPlaying();
                    if ((indexPosition < mFirstVisibleItemPosition
                            || indexPosition > mLastVisibleItemPosition)
                            ) {

                        VideoSuperPlayer currPlayPlayer = adapter.getCurrPlayPlayer();
                        if (currPlayPlayer != null) {
                            VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = currPlayPlayer.getVideoPlayCallback();
                            if (videoPlayCallback != null) {
                                videoPlayCallback.onCloseVideo();
                            }
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

    class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }
}
