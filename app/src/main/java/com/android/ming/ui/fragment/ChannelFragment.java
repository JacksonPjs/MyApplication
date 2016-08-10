package com.android.ming.ui.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.android.ming.bean.Channel;
import com.android.ming.presenter.ChannelPresenter;
import com.android.ming.ui.activity.ListActivity;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.ChannelAdapter;
import com.android.ming.ui.view.IChannelView;
import com.android.ming.utils.ToastUtil;

import java.util.List;
/*
* 频道页
* */
public class ChannelFragment extends RefreshableFragment implements IChannelView, BaseRecyclerAdapter.OnItemClickListener {
    private ChannelAdapter adapter;
    private ChannelPresenter mPresenter;

    @Override
    public void onFragmentCreate() {
        adapter = new ChannelAdapter(activity);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
        mPresenter = new ChannelPresenter(this, activity.getQueue());
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                mPresenter.loadList();
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.loadList();

    }

    @Override
    public void showList(List<Channel> channels) {
        refreshLayout.setRefreshing(false);
        adapter.refreshData(channels);
    }

    @Override
    public void showError(String msg) {
        refreshLayout.setRefreshing(false);
        ToastUtil.show(activity, msg);
    }

    @Override
    public void onItemClick(View v, int position) {
        startActivity(ListActivity.createIntent(activity, adapter.getItem(position).getId(), adapter.getItem(position).getTitle()));
    }
}
