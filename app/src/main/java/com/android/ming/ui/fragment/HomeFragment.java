package com.android.ming.ui.fragment;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ming.ui.activity.WappayTest;
import com.android.volley.toolbox.ImageLoader;
import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.presenter.HomePresenter;
import com.android.ming.ui.activity.VideoActivity;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.VideoHeaderAdapter;
import com.android.ming.ui.view.IHomeView;
import com.android.ming.ui.widget.BannerView;
import com.android.ming.utils.ToastUtil;

import java.util.List;
import java.util.Random;

/*
    首页
* */
public class HomeFragment extends RefreshableFragment implements BannerView.BannerViewListener, IHomeView, BaseRecyclerAdapter.OnItemClickListener {
        private BannerView bannerView;
        private VideoHeaderAdapter adapter;
        private HomePresenter mPresenter;


        @Override
        public void onFragmentCreate() {
                bannerView = new BannerView(activity);
                adapter = new VideoHeaderAdapter(activity, bannerView);
                adapter.setOnItemClickListener(this);
                recyclerView.setAdapter(adapter);
                final GridLayoutManager manager = new GridLayoutManager(activity, 2);
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
                        }
                });
                recyclerView.setLayoutManager(manager);
                mPresenter = new HomePresenter(this, activity.getQueue());
                refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                                refreshLayout.setRefreshing(true);
                                mPresenter.loadHomeData();
                        }
                });

        }

        @Override
        public void showHomeData(List<Video> banner, List<Video> videos) {
                refreshLayout.setRefreshing(false);
                bannerView.setData(banner, this);
                adapter.refreshData(videos);
        }

        @Override
        public void showError(String msg) {
                refreshLayout.setRefreshing(false);
                ToastUtil.show(activity, msg);
        }

        @Override
        public void onRefresh() {
                mPresenter.loadHomeData();
        }


        @Override
        public void onBannerClick(int position, Video video) {
                startActivity(VideoActivity.createIntent(activity, video.getId()));
        }

        @Override
        public void displayImage(ImageView imageView, String url) {
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.video_loading, R.mipmap.video_loading);
                activity.getImageLoader().get(url, imageListener);
        }

        @Override
        public void onItemClick(View v, int position) {
        startActivity(VideoActivity.createIntent(activity, adapter.getItem(position).getId()));
//                startActivity(WappayTest.createIntent(activity, adapter.getItem(position).getId()));
        }
}
