package com.android.ming.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ming.R;
import com.android.ming.bean.Channel;
import com.android.ming.bean.Video;
import com.android.ming.ui.activity.BaseActivity;
import com.android.ming.ui.activity.SearchActivity;

import java.util.List;

/**
 * Created by YO on 2016/8/13.
 */
public class SearchGridViewAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        Context context;
        List<Channel> videos;
        public SearchGridViewAdapter(Context context, List<Channel> cids) {
                this.context=context;
                videos=cids;
                layoutInflater = LayoutInflater.from(context);
        }
//


        @Override
        public int getCount() {
                return videos.size();
        }

        @Override
        public Object getItem(int position) {
                return videos.get(position);
        }

        @Override
        public long getItemId(int position) {
                return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View view = null;

                if (layoutInflater != null) {

                        view = layoutInflater
                                .inflate(R.layout.searchgrid_item_layout, null);

                        TextView textView = (TextView) view.findViewById(R.id.face);
                        //获取自定义的类实例
                        Log.e("sdsa",videos.get(position).toString());
                        textView.setText(videos.get(position).getTitle());

                }
                return view;
        }
}
