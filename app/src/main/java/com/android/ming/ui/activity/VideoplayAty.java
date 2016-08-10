package com.android.ming.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Video;
import com.android.ming.listener.ShowPdListener;
import com.android.ming.presenter.ListPresenter;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.VideoAdapter;
import com.android.ming.ui.view.CommonVideoView;
import com.android.ming.ui.view.IListView;
import com.android.ming.utils.SPUtil;

import java.util.List;

/**
 * Created by YO on 2016/7/14.
 */
public class VideoplayAty extends  BaseActivity implements BaseRecyclerAdapter.OnItemClickListener,IListView {

    private static String VIDEO_URL = "url";
    private static String IS_PORTRAIT = "portrait";
    private static String IS_FREE = "free";
    CommonVideoView videoView;
    protected RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ListPresenter presenter;
    private boolean isFree;// 是否免费
    TextView tips;

    public static Intent createIntent(Context context, String url, boolean isPortrait) {
        return createIntent(context, url, isPortrait, false);
    }
    public static Intent createIntent(Context context, String url, boolean isPortrait, boolean isFree) {
        Intent intent = new Intent(context, VideoplayAty.class);
        intent.putExtra(VIDEO_URL, url);
        intent.putExtra(IS_PORTRAIT, isPortrait);
        intent.putExtra(IS_FREE, isFree);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        videoView= (CommonVideoView) findViewById(R.id.common_videoView);
        tips= (TextView) findViewById(R.id.tips);
        final String url = getIntent().getStringExtra(VIDEO_URL);
        isFree = getIntent().getBooleanExtra(IS_FREE, false);
        videoView.start(url, new ShowPdListener() {
            @Override
            public void onShow() {
                if (isFree && SPUtil.getInt(VideoplayAty.this, Consts.SP.VIP) == 0){
                  Toast  toast = Toast.makeText(getApplicationContext(),
                            "试看结束，请开通会员", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
//                    Toast.makeText(VideoplayAty.this,"试看结束，请开通会员",Toast.LENGTH_SHORT).show();
                    PayActivity.createInstance(VideoplayAty.this, 1);
                }else {
                    videoView.start(url,null,1);
                }
            }


        },0);
        if (isFree && SPUtil.getInt(VideoplayAty.this, Consts.SP.VIP) == 0){
            tips.setText("试看20秒，开通会员观看完整视频");
        }else{
            tips.setText("");
        }
        presenter = new ListPresenter(this, getQueue());
        adapter = new VideoAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        GridLayoutManager manager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(manager);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
//        presenter.loadVideos(2, 1);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            videoView.setFullScreen();
        }else {
            videoView.setNormalScreen();
        }
    }


    @Override
    public void onItemClick(View v, int position) {
        startActivity(VideoActivity.createIntent(this, adapter.getItem(position).getId()));
    }

    @Override
    public void showVideos(List<Video> videos) {
        Log.e("videos==",videos.toString());
        adapter.refreshData(videos);
    }

    @Override
    public void showError(String msg) {Log.e("videos",msg);
    }
}
