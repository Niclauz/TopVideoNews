package cn.com.ichile.playertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import cn.com.ichile.pigplayer.core.PigPlayer;
import cn.com.ichile.pigplayer.core.manager.PlayerItemChangeListener;
import cn.com.ichile.pigplayer.core.meta.CurrentItemMeta;
import cn.com.ichile.pigplayer.core.meta.MetaData;
import cn.com.ichile.pigplayer.manager.SinglePlayerManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        MyAdapter adapter = new MyAdapter(null);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //http://f02.v1.cn/transcode/14430418MOBILET2.mp4

    //http://image.v1.cn/vodone/20161114/366106_0x0.jpg

    class MyAdapter extends RecyclerView.Adapter {
        List mData;
        View view;

        MyAdapter(List list) {
            mData = list;
        }

        @Override
        public int getItemCount() {
            return 15;
            // return mData == null ? 0 : mData.size();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            ItemHolder itemHolder = new ItemHolder(view);
            return itemHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            final ItemHolder itemHolder = (ItemHolder) holder;
            itemHolder.btn_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SinglePlayerManager singlePlayerManager = new SinglePlayerManager(new PlayerItemChangeListener() {
                        @Override
                        public void onPlayerItemChanged(MetaData currentItemMeta) {

                        }
                    });
                    singlePlayerManager.startNewPlay(new CurrentItemMeta(position, view), itemHolder.pigPlayer, "http://f02.v1.cn/transcode/14430418MOBILET2.mp4");
                    itemHolder.btn_play.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        private PigPlayer pigPlayer;
        private ImageView btn_play;

        public ItemHolder(View itemView) {
            super(itemView);
            pigPlayer = (PigPlayer) itemView.findViewById(R.id.pp_item);
            btn_play = (ImageView) itemView.findViewById(R.id.btn_play);
        }
    }
}
