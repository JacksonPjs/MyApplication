package com.android.ming.ui.adapter;

import android.content.Context;
import android.widget.ImageView;


import com.android.ming.R;
import com.android.ming.bean.Bean;
import com.android.ming.bean.Video;
import com.android.ming.ui.widget.CommonAdapter;
import com.android.ming.ui.widget.ViewHolder;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by yetwish on 2015-05-11
 */

public class SearchAdapter extends CommonAdapter<Video> {

    public SearchAdapter(Context context, List<Video> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        holder.setText(R.id.item_search_tv_title,mData.get(position).getTitle());
    }
}
