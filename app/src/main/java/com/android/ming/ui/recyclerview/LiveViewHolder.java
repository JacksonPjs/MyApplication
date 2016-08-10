package com.android.ming.ui.recyclerview;

import android.view.View;
import android.widget.ImageView;

import com.android.ming.R;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;

/**
 * Created by KingYang on 2016/4/30.
 * E-Mail: admin@kingyang.cn
 */
public class LiveViewHolder extends BaseViewHolder {
    public ImageView face;

    public LiveViewHolder(View itemView, final BaseRecyclerAdapter.OnItemClickListener listener) {
        super(itemView, listener);
        face = (ImageView) itemView.findViewById(R.id.face);
    }
}