package com.android.ming.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.ming.R;
import com.android.ming.bean.Channel;
import com.android.ming.ui.activity.BaseActivity;
import com.android.ming.ui.recyclerview.ChannelViewHolder;

/**
 * Created by KingYang on 16/3/31.
 * E-Mail: admin@kingyang.cn
 * 频道适配器
 */
public class ChannelAdapter extends BaseRecyclerAdapter<Channel, ChannelViewHolder> {

    public ChannelAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channel, parent, false);
        return new ChannelViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        Channel channel = getItem(position);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.face, R.mipmap.video_loading, R.mipmap.video_loading);
        activity.getImageLoader().get(channel.getFace()+"", listener);
        holder.title.setText(channel.getTitle());
//        holder.update.setText(String.format("今日更新%d部", channel.getUpdate()));
    }

}

