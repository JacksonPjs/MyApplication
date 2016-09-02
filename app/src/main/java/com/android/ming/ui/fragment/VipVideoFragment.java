package com.android.ming.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.presenter.HomePresenter;
import com.android.ming.ui.activity.MainActivity;
import com.android.ming.ui.activity.VideoActivity;
import com.android.ming.ui.view.IHomeView;
import com.android.ming.ui.widget.BannerView;
import com.android.ming.ui.widget.VipBannerView;
import com.android.ming.utils.ComTools;
import com.android.ming.utils.ScreenUtils;
import com.android.ming.utils.ToastUtil;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by YO on 2016/8/25.
 */
public class VipVideoFragment extends Fragment implements VipBannerView.BannerViewListener, IHomeView,View.OnClickListener{
        MainActivity activity;
        HomePresenter mPresenter;
        VipBannerView bannerView;
        List<Video> videos;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                activity = (MainActivity) getActivity();
                //hide the title bar
                bannerView=new VipBannerView(activity);
                View view =inflater.inflate(R.layout.fragment_vip,container,false);
                init(view);
                return view;
        }

        public void init(View view) {
                LinearLayout course_linearLayout  = (LinearLayout)view.findViewById(R.id.fragment_vip);
                course_linearLayout.addView(bannerView);
                mPresenter = new HomePresenter(this, activity.getQueue());
                mPresenter.loadHomeData();
        }

        @Override
        public void onBannerClick(int position, Video video) {
                startActivity(VideoActivity.createIntent(getActivity(), video.getId()));
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
        public void showHomeData(List<Video> banner, List<Video> videos) {
                bannerView.setData(banner, this);
                this.videos=videos;
        }

        @Override
        public void showError(String msg) {
                ToastUtil.show(getActivity(), msg);
        }

        @Override
        public void onClick(View v) {

        }
}
