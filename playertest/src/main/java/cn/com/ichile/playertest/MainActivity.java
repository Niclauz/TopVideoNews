package cn.com.ichile.playertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.ichile.pigplayer.core.manager.PlayerItemChangeListener;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.holder.VideoItemHolder;
import cn.com.ichile.pigplayer.item.BaseVideoItem;
import cn.com.ichile.pigplayer.item.NetVideoItem;
import cn.com.ichile.pigplayer.item.VideoItemFactory;
import cn.com.ichile.pigplayer.manager.SinglePlayerManager;

public class MainActivity extends AppCompatActivity {

    private SinglePlayerManager mSinglePlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager
                .VERTICAL, false));


        mSinglePlayerManager = new SinglePlayerManager(new PlayerItemChangeListener() {


            @Override
            public void onPlayerItemChanged(MetaData currentItemMeta) {

            }
        });
        ArrayList<BaseVideoItem> items = new ArrayList<>();
        String title = "i am title";
        String author = "i am author";
        String img = "http://image.v1.cn/vodone/20161114/366106_0x0.jpg";
        String url = "http://f02.v1.cn/transcode/14430418MOBILET2.mp4";
        items.add(VideoItemFactory.createItemFromNet(MainActivity.this, mSinglePlayerManager, title,
                img, author, url));

        items.add(VideoItemFactory.createItemFromNet(MainActivity.this, mSinglePlayerManager, title,
                img, author, url));

        items.add(VideoItemFactory.createItemFromNet(MainActivity.this, mSinglePlayerManager, title,
                img, author, url));

        MyAdapter adapter = new MyAdapter(items);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    //http://f02.v1.cn/transcode/14430418MOBILET2.mp4

    //http://image.v1.cn/vodone/20161114/366106_0x0.jpg

    class MyAdapter extends RecyclerView.Adapter<VideoItemHolder> {
        List<NetVideoItem> mData;

        MyAdapter(List list) {
            mData = list;
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public VideoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            NetVideoItem netVideoItem = mData.get(viewType);
            View view = netVideoItem.createView(parent, MainActivity.this.getResources()
                    .getDisplayMetrics().widthPixels);
            VideoItemHolder videoItemHolder = new VideoItemHolder(view);
            return videoItemHolder;
        }

        @Override
        public void onBindViewHolder(final VideoItemHolder holder, int position) {
            final NetVideoItem netVideoItem = mData.get(position);
            netVideoItem.updateView(position, holder, mSinglePlayerManager);
            holder.btn_item_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.iv_item_video_pre.setVisibility(View.GONE);
                    netVideoItem.playNewVideo(null,holder.video_player,mSinglePlayerManager);
                }
            });
        }


    }

}
