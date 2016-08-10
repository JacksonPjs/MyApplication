package com.android.ming.ui.recyclerview;

import android.view.View;
import android.widget.ImageView;

import com.android.ming.R;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;

/**
 * Created by YO on 2016/8/9.
 */
public class PicViewHolder extends BaseViewHolder {
        public ImageView face;
        public PicViewHolder(View itemView, BaseRecyclerAdapter.OnItemClickListener listener) {
                super(itemView, listener);
                face = (ImageView) itemView.findViewById(R.id.face);
        }
}
