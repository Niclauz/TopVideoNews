package cn.com.ichile.topvideonews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.topvideonews.Cons;
import cn.com.ichile.topvideonews.R;
import cn.com.ichile.topvideonews.adapter.FragmentAdapter;
import cn.com.ichile.topvideonews.db.OriginalDao;
import cn.com.ichile.topvideonews.domain.VideoBean;
import cn.com.ichile.topvideonews.fragment.BaseFragment;
import cn.com.ichile.topvideonews.fragment.HotFragment;
import cn.com.ichile.topvideonews.fragment.RecommendFragment;
import cn.com.ichile.topvideonews.fragment.TvFragment;
import cn.com.ichile.topvideonews.util.UiUtil;
import cn.com.ichile.topvideonews.widget.MediaHelp;
import cn.com.ichile.topvideonews.widget.RoundImageView;
import cn.sharesdk.login.UserInfo;
import cn.sharesdk.onekeyshare.ShareTool;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2016/12/28 - 20:48.
 */
public class MainActivity extends BaseActivity
        implements OnNavigationItemSelectedListener {


    public OriginalDao mDao;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private List<String> mPageTypeList;
    private int lastPosition;
    private FragmentAdapter mFragmentAdapter;
    private List<VideoBean> list;
    private BaseFragment mFragment;
    private TextView mNavHeadName;
    private TextView mNavHeadDesc;
    private RoundImageView mNavHeadIma;


    @Override
    public void baseOnCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                // refresh();

                ShareTool shareTool = new ShareTool(MainActivity.this);
                shareTool.showShare("title", "http://www.mob.com", "share share", null, "http://www.mob.com");
            }
        });

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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_top);
        mViewPager = (ViewPager) findViewById(R.id.vp_main);

        mDao = new OriginalDao(getApplicationContext(), null);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        mPageTypeList = new ArrayList();

        mPageTypeList.add(0, Cons.RECOMMEND);
        mPageTypeList.add(1, Cons.HOT);
        mPageTypeList.add(2, Cons.TV);

        titleList.add(0, "推荐");
        titleList.add(1, "焦点");
        titleList.add(2, "卫视");

        RecommendFragment recommendFragment = new RecommendFragment();
        HotFragment hotFragment = new HotFragment();
        TvFragment tvFragment = new TvFragment();

        fragmentList.add(0, recommendFragment);
        fragmentList.add(1, hotFragment);
        fragmentList.add(2, tvFragment);

        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mFragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);


        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                if (position == 0) {
//                    mFragment = (RecommendFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }else if(position == 1) {
//                    mFragment = (HotFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }else if (position == 2) {
//                    mFragment = (TvFragment) mFragmentAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
//                }
//                if (position == 0) {
//                    RecommendRecyAdapter videoListAdapter = (RecommendRecyAdapter) mFragment.getItemAdapter();
//                    NetUtil.getVideoList(mPageTypeList.get(position),videoListAdapter);
//                }else{
//                    VideoListAdapter videoListAdapter = (VideoListAdapter) mFragment.getItemAdapter();
//                    NetUtil.getVideoList(mPageTypeList.get(position),videoListAdapter);
//                }

                Toast.makeText(getApplicationContext(), "position-" + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        ViewPager.OnPageChangeListener onPageChangeListener = mOnPageChangeListener;
        mViewPager.addOnPageChangeListener(onPageChangeListener);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
            UiUtil.startActivity(MainActivity.this,SocialActivity.class);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
