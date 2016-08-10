package com.android.ming.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.ui.activity.BaseActivity;
import com.android.ming.ui.recyclerview.LiveViewHolder;
import com.android.ming.ui.recyclerview.PicViewHolder;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by YO on 2016/8/9.
 */
public class PicAdapter extends BaseRecyclerAdapter<Video, PicViewHolder> {
        public PicAdapter(BaseActivity activity) {
                super(activity);
        }

        @Override
        public PicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live,parent,false);
                return new PicViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(PicViewHolder holder, int position) {
                Video video = getItem(position);
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.face, R.mipmap.video_loading, R.mipmap.video_loading);
                activity.getImageLoader().get(video.getFace()+"", listener);
        }


}
