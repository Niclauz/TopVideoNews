package cn.com.ichile.topvideonews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.adapter.FragmentAdapter;
import cn.com.ichile.topvideonews.adapter.RecommendRecyAdapter;
import cn.com.ichile.topvideonews.callback.OnFragmentBind;
import cn.com.ichile.topvideonews.db.OriginalDao;
import cn.com.ichile.topvideonews.fragment.RecommendFragment;
import cn.com.ichile.topvideonews.net.DataUtil;
import cn.com.ichile.topvideonews.util.UiUtil;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.RoundImageView;
import cn.com.ichile.topvideonews.widget.VideoSuperPlayer;
import cn.sharesdk.login.UserInfo;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 20:48.
 */
public class MainActivity extends BaseActivity
        implements OnNavigationItemSelectedListener {


    private static final int INIT_SDK = 1;
    private static final int AFTER_LIKE = 2;
    public OriginalDao mDao;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private List<String> mPageTypeList;
    private int lastPosition;
    private FragmentAdapter mFragmentAdapter;
    private RecommendFragment mFragment;
    private RecyclerView mRecycleView;
    private TextView mNavHeadName;
    private TextView mNavHeadDesc;
    private RoundImageView mNavHeadIma;
    private Context context;


    @Override
    public void baseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        initFab();

        initNavigationBar(toolbar);

        initViewPager();

    }

    @Override
    public boolean hasToolBar() {
        return false;
    }

    @Override
    public String setToolBarTitle() {
        return null;
    }

    private void initNavigationBar(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNavHeadName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_nav_head_name);
        mNavHeadDesc = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_nav_head_desc);
        mNavHeadIma = (RoundImageView) navigationView.getHeaderView(0).findViewById(R.id.riv_nav_head);

        mNavHeadName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(i, Cons.LOGIN_RESULT);
            }
        });
    }

    private void initFab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecycleView != null) {
                    mRecycleView.smoothScrollToPosition(0);
                    Snackbar snackbar = Snackbar.make(view, "已滚动到顶部，下拉刷新!", Snackbar.LENGTH_SHORT)
                            .setAction("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorActionBar));
                    View snackbarView = snackbar.getView();
                    TextView tv = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                    Button button = (Button) snackbarView.findViewById(R.id.snackbar_action);
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    button.setTextColor(getResources().getColor(R.color.colorWhite));
                    snackbar.show();
                }
            }
        });
    }


    public ViewPager getViewPager() {
        return mViewPager;
    }

    private int currPagerPosition;

    public int getPagerPosition() {
        return currPagerPosition;
    }

    private void initViewPager() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_top);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);

        //mDao = new OriginalDao(getApplicationContext(), null);

        mPageTypeList = new ArrayList();

        mPageTypeList.add(0, Cons.RECOMMEND);
        mPageTypeList.add(1, Cons.HOT);
        mPageTypeList.add(2, Cons.TV);


        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), tabLayout, new OnFragmentBind() {

            @Override
            public void onBind(Fragment fragment) {
                if (fragment != null) {
                    mFragment = (RecommendFragment) fragment;
                    mRecycleView = mFragment.getRecycleView();
                }
            }
        });
        //***********获取tab列表
        DataUtil.getTabListType(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currPagerPosition = position;
                mFragment = (RecommendFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
                mRecycleView = mFragment.getRecycleView();
                RecommendRecyAdapter adapter = (RecommendRecyAdapter) mRecycleView.getAdapter();
                VideoSuperPlayer currPlayPlayer = adapter.getCurrPlayPlayer();
                if (currPlayPlayer != null) {
//                    VideoSuperPlayer.VideoPlayCallbackInterface videoPlayCallback = currPlayPlayer.getVideoPlayCallback();
//                    if (videoPlayCallback != null) {
//                        videoPlayCallback.onCloseVideo();
//                    }
                    currPlayPlayer.setVisibility(View.GONE);
                }
//                if (position == 0) {
//                    mFragment = (RecommendFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }else if(position == 1) {
//                    mFragment = (HotFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }else if (position == 2) {
//                    mFragment = (TvFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }
//                if (position == 0) {
//                    RecommendRecyAdapter videoListAdapter = (RecommendRecyAdapter) mFragment.getItemAdapter();
//                    DataUtil.getVideoList(mPageTypeList.get(position),videoListAdapter);
//                }else{
//                    VideoListAdapter videoListAdapter = (VideoListAdapter) mFragment.getItemAdapter();
//                    DataUtil.getVideoList(mPageTypeList.get(position),videoListAdapter);
//                }

                Toast.makeText(getApplicationContext(), "position-" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Cons.LOGIN_RESULT && data != null) {

            mNavHeadName.setOnClickListener(null);
            UserInfo info = (UserInfo) data.getExtras().getSerializable("loginInfo");
            mNavHeadName.setText(info == null ? "hello" : info.getUserName());
            mNavHeadDesc.setText(info == null ? "hello" : info.getUserNote());
            mNavHeadDesc.setVisibility(View.VISIBLE);
            try {

                Picasso.with(MainActivity.this)
                        .load(info.getUserIcon())
                        .placeholder(getResources().getDrawable(R.drawable.ic_launcher))
                        .into(mNavHeadIma);
                // mNavHeadIma.setImageURI(Uri.parse(info.getUserIcon()));
                // mNavHeadIma.setImageDrawable(getResources().getDrawable(R.drawable.ssdk_oks_classic_qq));
            } catch (Exception e) {
                mNavHeadIma.setImageResource(R.drawable.ic_launcher);
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        MediaHelp.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        MediaHelp.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaHelp.release();
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_collection) {
            UiUtil.startActivity(MainActivity.this, CollectionActivity.class);
        } else if (id == R.id.nav_history) {
            UiUtil.startActivity(MainActivity.this, HistoryActivity.class);
        } else if (id == R.id.nav_more) {
            UiUtil.startActivity(MainActivity.this, MoreAvtivity.class);
        } else if (id == R.id.nav_setting) {
            UiUtil.startActivity(MainActivity.this, SettingsActivity.class);
        } else if (id == R.id.nav_share) {
            //..........
        } else if (id == R.id.nav_feedback) {
            UiUtil.startActivity(MainActivity.this, FeedbackActivity.class);
        } else if (id == R.id.nav_about) {
            UiUtil.startActivity(MainActivity.this, AboutActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
