package com.android.ming.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.utils.ComTools;
import com.android.ming.utils.DisplayUtil;
import com.android.ming.utils.ScreenUtils;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YO on 2016/8/25.
 */
public class VipBannerView extends FrameLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {
        private int mCount;
        private Context mContext;
        private int currentItem;
        private List<Video> mData = new ArrayList<>();
        private ViewPager viewPager;
        private LinearLayout dotContainer;
        private LinearLayout dotsLin;
        private List<ImageView> images = new ArrayList<>();
        private List<ImageView> dots = new ArrayList<>();
        private List<ImageView> dotim = new ArrayList<>();
        private BannerViewListener mListener;
        ImageView dotView;
//


        public VipBannerView(Context context, AttributeSet attrs, int defStyle) {
                super(context, attrs, defStyle);
                mContext = context;

                int hei= ScreenUtils.getScreenHeight(context);
                int hei2=DisplayUtil.px2dip(context, hei);
                int t=ScreenUtils.getStatusHeight(context);
                Log.e("hei",hei+"hei2=="+hei2+"t=="+t);
                // 默认高度180dp
                setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 470)));
                dotsLin = new LinearLayout(mContext);
                addView(dotsLin);
                // 图片ViewPager
                viewPager = new ViewPager(mContext);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                addView(viewPager, layoutParams1);
                // 指示器容器
                dotContainer = new LinearLayout(mContext);

                LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
                addView(dotContainer, layoutParams2);

        }

        public VipBannerView(Context context, AttributeSet attrs) {
                this(context, attrs, 0);
        }

        public VipBannerView(Context context) {
                this(context, null);
        }

        public void setData(List<Video> data, BannerViewListener listener) {
                mData = data;
                mListener = listener;
                initView();
                showTime();
        }

        /**
         * 初始化界面
         */
        private void initView() {
                mCount = mData.size();
                // 清除指示器
                dotContainer.removeAllViews();
                dotsLin.removeAllViews();
                dots.clear();
                dotim.clear();

                // 装载指示器
                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, 400));
//                layoutParams5.rightMargin = DisplayUtil.dip2px(getContext(), 10);
                layoutParams5.setMargins(DisplayUtil.dip2px(getContext(), 50),DisplayUtil.dip2px(getContext(), 50),DisplayUtil.dip2px(getContext(), 50),DisplayUtil.dip2px(getContext(), 50));
                for (int i = 0; i < mCount; i++) {
                         dotView = new ImageView(mContext);
                        dotView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Video video=mData.get(i);
                        // 显示图片
                        if (mListener != null) {
                                mListener.displayImage(dotView, video.getFace());
                        }
                        dotContainer.addView(dotView,layoutParams5);
                        dots.add(dotView);
                }
                for (int i = 0; i < mCount; i++) {
                       ImageView dot = new ImageView(mContext);

                        dotContainer.addView(dot);
                        dotim.add(dot);
                }


                // 清除图片
                images.clear();

                // 装载count+2张图片
                for (int i = 0; i <= mCount + 1; i++) {
                        ImageView imageView = new ImageView(mContext);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Video video;
                        if (i == 0) {// 第一个位置装载最后一个图片
                                video = mData.get(mCount - 1);
                        } else if (i == mCount + 1) {// 最后一个位置装载第一张图片
                                video = mData.get(0);
                        } else {
                                video = mData.get(i - 1);
                                imageView.setOnClickListener(this);// OnItemClick
                        }
                        // 显示图片
                        if (mListener != null) {
                                mListener.displayImage(imageView, video.getFace());
                        }
                        images.add(imageView);
                }
        }

        private void showTime() {
                viewPager.setAdapter(new ViewAdapter());
                viewPager.addOnPageChangeListener(this);
                if (mCount > 0) {// 数据列表不为空
                        viewPager.setCurrentItem(1);
                        showView(1);
                        currentItem = 1;
//                        new Handler() {
//                                @Override
//                                public void handleMessage(Message msg) {
//                                        handler.postDelayed(autoPlay, delayTime);
//                                }
//                        }.sendEmptyMessage(0);
                }
        }


        /**
         * 显示指示器
         *
         * @param position 位置
         */
        private void showView(int position) {
                for (int i = 0; i < dots.size(); i++) {
                        Video video=mData.get(position-1);
                        mListener.displayImage( dots.get(i), video.getFace());
                }
                for (int i = 0; i < dotim.size(); i++) {
                        int resId = (i == position - 1) ? R.drawable.indicator_focused : R.drawable.indicator_normal;
                        dotim.get(i).setImageResource(resId);
                }


                Animation scaleAnimation = new ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                scaleAnimation.setDuration(5000);
                scaleAnimation.setFillAfter(true);// 播放完毕停在最后一帧
                images.get(position).setAnimation(scaleAnimation);

        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
                int position = viewPager.getCurrentItem();
                switch (state) {
                        case 1:// 正在滑动,只有手动操作才会走此逻辑
//                                handler.removeCallbacks(autoPlay);
//                                handler.postDelayed(autoPlay, delayTime);
                                break;
                        case 2:// 滑动完毕
                                if (position == 0) {
                                        showView(mCount);
                                } else if (position == mCount + 1) {
                                        showView(1);
                                } else {
                                        showView(position);
                                }
                                break;
                        case 0:
                                if (position == 0) {// 第一个位置(0)直接跳转到倒数第2个位置
                                        viewPager.setCurrentItem(mCount, false);
                                } else if (position == mCount + 1) {// 最后一个位置直接跳转到第2个位置
                                        viewPager.setCurrentItem(1, false);
                                }
                                currentItem = viewPager.getCurrentItem();
                                break;
                }
        }

        @Override
        public void onClick(View v) {
                if (mListener != null) {
                        int position = viewPager.getCurrentItem() - 1;
                        mListener.onBannerClick(position, mData.get(position));
                }
        }

        private class ViewAdapter extends PagerAdapter {

                @Override
                public int getCount() {
                        return images.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object obj) {
                        return view == obj;
                }

                @Override
                public ImageView instantiateItem(ViewGroup container, int position) {
                        container.addView(images.get(position));
                        return images.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView(images.get(position));
                }

        }

        public interface BannerViewListener {
                void onBannerClick(int position, Video video);

                void displayImage(ImageView imageView, String url);
        }

        @Override
        protected void onDetachedFromWindow() {
                // 取消定时器
//                handler.removeCallbacks(autoPlay);
                super.onDetachedFromWindow();
        }
}
