package com.android.ming.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.banner.SimpleImageBanner;
import com.android.ming.bean.Video;
import com.android.ming.presenter.HomePresenter;
import com.android.ming.presenter.ListPresenter;
import com.android.ming.ui.activity.MainActivity;
import com.android.ming.ui.activity.NewPayActivity;
import com.android.ming.ui.activity.PayActivity;
import com.android.ming.ui.activity.VideoActivity;
import com.android.ming.ui.activity.VideoplayAty;
import com.android.ming.ui.adapter.ImageViewPagerAdpater;
import com.android.ming.ui.view.IHomeView;
import com.android.ming.ui.view.IListView;
import com.android.ming.ui.widget.VipBannerView;
import com.android.ming.utils.ComTools;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;
import com.android.ming.utils.ViewFindUtils;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.flyco.pageindicator.anim.select.ZoomInEnter;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YO on 2016/8/26.
 */
public class VideoFragment extends Fragment implements IListView,View.OnClickListener,SimpleImageBanner.BannerViewListener, ViewPager.OnPageChangeListener {
        MainActivity activity;
        List<Video> videos;
        private int[] resIds = {R.mipmap.ic_avatar_default, R.mipmap.ic_launcher,
                R.mipmap.ic_fast_forward_white_24dp, R.mipmap.search_icon};
        private ArrayList<Integer> resList = new ArrayList<>();;
        private View decorView;
        private SimpleImageBanner banner;
        RelativeLayout bg;
        FlycoPageIndicaor   indicator;
        int pos=0;
        View view;
        private ViewPager viewPager;
        int mCount=0;
        ImageView vipLock;
        ListPresenter presenter;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                activity = (MainActivity) getActivity();
                //hide the title bar
                 view =inflater.inflate(R.layout.fragment_video,container,false);
                init(view);
                return view;
        }
        public  void init(View view){
                presenter = new ListPresenter(this, activity.getQueue());

                presenter.loadVideos(102, 1);


                decorView = activity.getWindow().getDecorView();
                banner= (SimpleImageBanner) view.findViewById(R.id.banner);
                   indicator = (FlycoPageIndicaor) view.findViewById(R.id.indicator_circle_anim);
                bg= (RelativeLayout) view.findViewById(R.id.bg);
                viewPager = (ViewPager)view.findViewById(R.id.viewpager);
                vipLock= (ImageView) view.findViewById(R.id.vip_lock);
                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                        vipLock.setVisibility(View.GONE);
                } else {
                        vipLock.setVisibility(View.VISIBLE);

                }

        }

        @Override
        public void onResume() {
                super.onResume();
                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                        vipLock.setVisibility(View.GONE);
                } else {
                        vipLock.setVisibility(View.VISIBLE);

                }

        }


        @Override
        public void showVideos(final List<Video> banne) {
                this.videos=banne;
                banner.setVideo(banne,this);
                for (int i = 0; i < banne.size(); i++) {
                        resList.add(i);
                }
                mCount=banne.size();
                viewPager.setAdapter(new PagerAdapter() {
                        @Override
                        public int getCount() {
                                return banne.size();
                        }

                        @Override
                        public boolean isViewFromObject(View view, Object object) {
                                return view==object;
                        }

                        @Override
                        public Object instantiateItem(ViewGroup container, final int position) {
//                                View view=LayoutInflater.from(activity).inflate(R.layout.fragment_video,container,false);

                                ImageView imageView = new ImageView(getActivity());
//                                imageView.setImageResource(resIds[position]);
                                displayImage(imageView,banne.get(position).getFace());
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Animation scaleAnimation = new ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
                                scaleAnimation.setDuration(5000);
                                scaleAnimation.setFillAfter(true);// 播放完毕停在最后一帧
//                                imageView.setAnimation(scaleAnimation);
                                container.addView(imageView);
                                container.setAnimation(scaleAnimation);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                                                startActivity(VideoActivity.createIntent(activity, banne.get(position).getId()));
                                                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                                                        if (SPUtil.getInt(activity, Consts.SP.VIP) == 5){
                                                                Log.e("viplv",SPUtil.getInt(activity, Consts.SP.VIP)+"");
                                                                startActivity(VideoplayAty.createIntent(activity, banne.get(position).getUrl(), false, false));
                                                        }else {
                                                                PayActivity.createInstance(activity, 5);
                                                        }


                                                } else {
                                                        Toast.makeText(getActivity(),"请先开通会员",Toast.LENGTH_SHORT).show();
                                                        NewPayActivity.createInstance(activity);


                                                }

                                        }
                                });
//                                viewPager.setObjectForPosition(imageView, position);
                                return imageView;

                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                                container.removeView((View) object);
                        }

                });

                viewPager.addOnPageChangeListener(this);

                final RequestQueue mQueue = Volley.newRequestQueue(activity);
                ImageRequest imageRequest = new ImageRequest(
                        banne.get(0).getFace(),
                        new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap response) {
                                        bg.setBackground(new BitmapDrawable(response));
                                }
                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                });
                mQueue.add(imageRequest);
                this.banner.setSource(resList).startScroll();
                banner.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                                pos=position;
                                if(position==videos.size()-1){
                                        if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                                                if (SPUtil.getInt(activity, Consts.SP.VIP) == 5){

                                                }else {
                                                        PayActivity.createInstance(activity, 5);
                                                }


                                        } else {
                                                Toast.makeText(getActivity(),"请先开通会员",Toast.LENGTH_SHORT).show();
                                                NewPayActivity.createInstance(activity);


                                        }
                                }
                                viewPager.setCurrentItem(position);
                                ImageRequest imageRequest = new ImageRequest(
                                        banne.get(pos).getFace(),
                                        new Response.Listener<Bitmap>() {
                                                @Override
                                                public void onResponse(Bitmap response) {
                                                        bg.setBackground(new BitmapDrawable(response));
                                                }
                                        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                });
                                mQueue.add(imageRequest);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                        }
                });
                indicatorAnim();

                banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                        @Override
                        public void onItemClick(int position) {
                                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                                        if (SPUtil.getInt(activity, Consts.SP.VIP) == 5){
                                                startActivity(VideoplayAty.createIntent(activity, banne.get(position).getUrl(), false, false));
                                        }else {
                                                PayActivity.createInstance(activity, 5);
                                        }


                                } else {
                                        Toast.makeText(getActivity(),"请先开通会员",Toast.LENGTH_SHORT).show();
                                        NewPayActivity.createInstance(activity);


                                }
                        }
                });
        }


        @Override
        public void showError(String msg) {
                ToastUtil.show(getActivity(), msg);
        }

        @Override
        public void onClick(View v) {

        }
        private void indicatorAnim() {
                indicator
                        .setIsSnap(true)
                        .setSelectAnimClass(ZoomInEnter.class)
                        .setViewPager(banner.getViewPager(), resList.size());
        }



        @Override
        public void displayImage(ImageView imageView, String url) {
                boolean isFileExist = ComTools.isFileExist(url, activity);

                if (isFileExist) {
                        Bitmap bitmap = ComTools.getBitmap2(url, activity);
                        imageView.setImageBitmap(bitmap);

                }else {
                        //如果有网络的话，再通过vollery调用
                        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.color.windowBackground, R.color.windowBackground);
                        activity.getImageLoader().get(url, imageListener);
                }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }


        @Override
        public void onPageSelected(int position) {
//                banner.setCurrentIndicator(position);
//                startActivity(VideoActivity.createIntent(activity, videos.get(position).getId()));
                banner.getViewPager().setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
}
