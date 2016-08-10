package com.android.ming.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.android.ming.app.Consts;
import com.android.ming.bean.Video;
import com.android.ming.presenter.LivePresenter;
import com.android.ming.ui.activity.PayActivity;
import com.android.ming.ui.activity.VideoPlayActivity;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.LiveAdapter;
import com.android.ming.ui.view.ILiveView;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;

import java.util.List;

public class LiveFragment extends RefreshableFragment implements ILiveView, BaseRecyclerAdapter.OnItemClickListener {
    private LiveAdapter adapter;
    private LivePresenter mPresenter;

    @Override
    public void onFragmentCreate() {
        adapter = new LiveAdapter(activity);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        mPresenter = new LivePresenter(this, activity.getQueue());
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                mPresenter.loadLives();
            }
        });
    }

    @Override
    public void showLives(List<Video> lives) {
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
        mPresenter.loadLives();
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
