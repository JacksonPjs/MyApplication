package com.android.ming.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by YO on 2016/8/26.
 */
public class ImageViewPagerAdpater extends PagerAdapter {
        List<View> views;
        @Override
        public int getCount() {
                return Integer.MAX_VALUE;
        }
        public ImageViewPagerAdpater(List<View> views){
                this.views=views;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
                return view == object ;
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));

        }
}
