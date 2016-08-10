package com.android.ming.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.android.ming.app.Consts;
import com.android.ming.bean.Video;
import com.android.ming.presenter.LivePresenter;
import com.android.ming.presenter.PicPresenter;
import com.android.ming.ui.activity.PayActivity;
import com.android.ming.ui.activity.VideoPlayActivity;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.LiveAdapter;
import com.android.ming.ui.adapter.PicAdapter;
import com.android.ming.ui.view.IPicView;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;

import java.util.List;

/**
 * Created by YO on 2016/8/9.
 */
public class PicFragment extends RefreshableFragment implements IPicView ,BaseRecyclerAdapter.OnItemClickListener{
        private PicAdapter adapter;
        private PicPresenter mPresenter;
        @Override
        public void onFragmentCreate() {
                adapter = new PicAdapter(activity);
                adapter.setOnItemClickListener(this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(activity, 3));
                mPresenter = new PicPresenter(this, activity.getQueue());
                refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                                refreshLayout.setRefreshing(true);
                                mPresenter.loadPic();
                        }
                });
        }
        @Override
        public void showPics(List<Video> lives) {
                refreshLayout.setRefreshing(false);
                adapter.refreshData(lives);
        }

        @Override
        public void showError(String msg) {
                refreshLayout.setRefreshing(false);
                ToastUtil.show(activity, msg);
        }
        @Override
        public void onRefresh() {
                mPresenter.loadPic();
        }
        @Override
        public void onItemClick(View v, int position) {
                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                        String url = adapter.getItem(position).getUrl();
                        startActivity(VideoPlayActivity.createIntent(activity, url, true));
                } else {
                        PayActivity.createInstance(activity, 1);
                }
        }
}
