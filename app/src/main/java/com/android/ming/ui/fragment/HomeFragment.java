package com.android.ming.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ming.app.Consts;
import com.android.ming.ui.activity.ListActivity;
import com.android.ming.ui.activity.MainActivity;
import com.android.ming.ui.activity.WappayTest;
import com.android.ming.utils.ComTools;
import com.android.volley.toolbox.ImageLoader;
import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.presenter.HomePresenter;
import com.android.ming.ui.activity.VideoActivity;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.VideoHeaderAdapter;
import com.android.ming.ui.view.IHomeView;
import com.android.ming.ui.widget.BannerView;
import com.android.ming.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    首页
* */
public class HomeFragment extends Fragment implements BannerView.BannerViewListener, IHomeView ,View.OnClickListener{
        private BannerView bannerView;
        private VideoHeaderAdapter adapter;
        private HomePresenter mPresenter;
        MainActivity activity;
        List<Video> videos;
        TextView tv_tryone,tv02,tv11,tv12,tv13,tv14,tv15,tv21,tv22,tv23,tv24,tv25,tv31,tv32,tv33,tv34,tv35,tv36,tv41,tv42,tv43,tv44,tv45,tv46,tv47,tv48,tv49;
        ImageView im01,im02,im11,im12,im13,im14,im15,im21,im22,im23,im24,im25,im31,im32,im33,im34,im35,im36,im41,im42,im43,im44,im45,im46,im47,im48,im49;
        RelativeLayout firstTab,secondTab,thTab,fourTab;
        RelativeLayout tryOne,tryTwo,firstOne,firstTwo,firstThree,fistFour,fistFive,secondOne,secondTwo,secondThree,secondFour,secondFive;
        RelativeLayout thOne,thTwo,thThree,thFour,thFive,thSix,fourOne,fourTwo,fourThree,fourFour,fourFive,fourSix,fourSeven,fourEight,fourNine;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                activity = (MainActivity) getActivity();
                View view =inflater.inflate(R.layout.fragment_home,container,false);
                view=course(view);
                init(view);
                return view;
        }

        public View course(View cFragment){
                //找到周几的线性布局
                LinearLayout course_linearLayout  = (LinearLayout)cFragment.findViewById(R.id.fragment_bome);
                bannerView = new BannerView(activity);
                course_linearLayout.addView(bannerView);
                View view =LayoutInflater.from(getActivity()).inflate(R.layout.item_home,null);
                course_linearLayout.addView(view);

                return cFragment;
        }
        public void init(View view){
                 videos=new ArrayList<>();
                tv_tryone= (TextView) view.findViewById(R.id.tv01);
                tv02= (TextView) view.findViewById(R.id.tv02);
                tv11= (TextView) view.findViewById(R.id.tv11);
                tv12= (TextView) view.findViewById(R.id.tv12);
                tv13= (TextView) view.findViewById(R.id.tv13);
                tv14= (TextView) view.findViewById(R.id.tv14);
                tv15= (TextView) view.findViewById(R.id.tv15);
                tv21= (TextView) view.findViewById(R.id.tv21);
                tv22= (TextView) view.findViewById(R.id.tv22);
                tv23= (TextView) view.findViewById(R.id.tv23);
                tv24= (TextView) view.findViewById(R.id.tv24);
                tv25= (TextView) view.findViewById(R.id.tv25);
                tv31= (TextView) view.findViewById(R.id.tv31);
                tv32= (TextView) view.findViewById(R.id.tv32);
                tv33= (TextView) view.findViewById(R.id.tv33);
                tv34= (TextView) view.findViewById(R.id.tv34);
                tv35= (TextView) view.findViewById(R.id.tv35);
                tv36= (TextView) view.findViewById(R.id.tv36);
                tv41= (TextView) view.findViewById(R.id.tv41);
                tv42= (TextView) view.findViewById(R.id.tv42);
                tv43= (TextView) view.findViewById(R.id.tv43);
                tv44= (TextView) view.findViewById(R.id.tv44);
                tv45= (TextView) view.findViewById(R.id.tv45);
                tv46= (TextView) view.findViewById(R.id.tv46);
                tv47= (TextView) view.findViewById(R.id.tv47);
                tv48= (TextView) view.findViewById(R.id.tv48);
                tv49= (TextView) view.findViewById(R.id.tv49);

                im01= (ImageView) view.findViewById(R.id.im01);
                im02= (ImageView) view.findViewById(R.id.im02);
                im11= (ImageView) view.findViewById(R.id.im11);
                im12= (ImageView) view.findViewById(R.id.im12);
                im13= (ImageView) view.findViewById(R.id.im13);
                im14= (ImageView) view.findViewById(R.id.im14);
                im15= (ImageView) view.findViewById(R.id.im15);
                im21= (ImageView) view.findViewById(R.id.im21);
                im22= (ImageView) view.findViewById(R.id.im22);
                im23= (ImageView) view.findViewById(R.id.im23);
                im24= (ImageView) view.findViewById(R.id.im24);
                im25= (ImageView) view.findViewById(R.id.im25);
                im31= (ImageView) view.findViewById(R.id.im31);
                im32= (ImageView) view.findViewById(R.id.im32);
                im33= (ImageView) view.findViewById(R.id.im33);
                im34= (ImageView) view.findViewById(R.id.im34);
                im35= (ImageView) view.findViewById(R.id.im35);
                im36= (ImageView) view.findViewById(R.id.im36);
                im41= (ImageView) view.findViewById(R.id.im41);
                im42= (ImageView) view.findViewById(R.id.im42);
                im43= (ImageView) view.findViewById(R.id.im43);
                im44= (ImageView) view.findViewById(R.id.im44);
                im45= (ImageView) view.findViewById(R.id.im45);
                im46= (ImageView) view.findViewById(R.id.im46);
                im47= (ImageView) view.findViewById(R.id.im47);
                im48= (ImageView) view.findViewById(R.id.im48);
                im49= (ImageView) view.findViewById(R.id.im49);

                firstTab= (RelativeLayout) view.findViewById(R.id.fisttab);
                secondTab= (RelativeLayout) view.findViewById(R.id.secontab);
                thTab= (RelativeLayout) view.findViewById(R.id.thtab);
                fourTab= (RelativeLayout) view.findViewById(R.id.fourtab);

                tryOne= (RelativeLayout) view.findViewById(R.id.tryone);
                tryTwo= (RelativeLayout) view.findViewById(R.id.trytwo);
                firstOne= (RelativeLayout) view.findViewById(R.id.fistone);
                firstTwo= (RelativeLayout) view.findViewById(R.id.fisttwo);
                firstThree= (RelativeLayout) view.findViewById(R.id.fisth);
                fistFour= (RelativeLayout) view.findViewById(R.id.fistf);
                fistFive= (RelativeLayout) view.findViewById(R.id.fistfi);

                secondOne= (RelativeLayout) view.findViewById(R.id.secondone);
                secondTwo= (RelativeLayout) view.findViewById(R.id.secondtwo);
                secondThree= (RelativeLayout) view.findViewById(R.id.secondth);
                secondFour= (RelativeLayout) view.findViewById(R.id.secondf);
                secondFive= (RelativeLayout) view.findViewById(R.id.secondfi);

                thOne= (RelativeLayout) view.findViewById(R.id.thone);
                thTwo= (RelativeLayout) view.findViewById(R.id.thtwo);
                thThree= (RelativeLayout) view.findViewById(R.id.tht);
                thFour= (RelativeLayout) view.findViewById(R.id.thtf);
                thFive= (RelativeLayout) view.findViewById(R.id.thfi);
                thSix= (RelativeLayout) view.findViewById(R.id.thsix);

                fourOne= (RelativeLayout) view.findViewById(R.id.fourone);
                fourTwo= (RelativeLayout) view.findViewById(R.id.fourtwo);
                fourThree= (RelativeLayout) view.findViewById(R.id.fourth);
                fourFour= (RelativeLayout) view.findViewById(R.id.fourf);
                fourFive= (RelativeLayout) view.findViewById(R.id.fourfive);
                fourSix= (RelativeLayout) view.findViewById(R.id.foursix);
                fourSeven= (RelativeLayout) view.findViewById(R.id.fourseven);
                fourEight= (RelativeLayout) view.findViewById(R.id.foure);
                fourNine= (RelativeLayout) view.findViewById(R.id.fourn);

                firstTab.setOnClickListener(this);
                secondTab.setOnClickListener(this);
                thTab.setOnClickListener(this);
                fourTab.setOnClickListener(this);

                tryOne.setOnClickListener(this);
                tryTwo.setOnClickListener(this);
                firstTwo.setOnClickListener(this);
                firstOne.setOnClickListener(this);
                firstThree.setOnClickListener(this);
                fistFour.setOnClickListener(this);
                fistFive.setOnClickListener(this);
                secondOne.setOnClickListener(this);
                secondTwo.setOnClickListener(this);
                secondThree.setOnClickListener(this);
                secondFour.setOnClickListener(this);
                secondFive.setOnClickListener(this);
                thOne.setOnClickListener(this);
                thTwo.setOnClickListener(this);
                thThree.setOnClickListener(this);
                thFour.setOnClickListener(this);
                thFive.setOnClickListener(this);
                thSix.setOnClickListener(this);
                fourOne.setOnClickListener(this);
                fourTwo.setOnClickListener(this);
                fourThree.setOnClickListener(this);
                fourFour.setOnClickListener(this);
                fourFive.setOnClickListener(this);
                fourSeven.setOnClickListener(this);
                fourSix.setOnClickListener(this);
                fourEight.setOnClickListener(this);
                fourNine.setOnClickListener(this);


                mPresenter = new HomePresenter(this, activity.getQueue());
                mPresenter.loadHomeData();

        }
        //        @Override
//        public void onFragmentCreate() {
//                bannerView = new BannerView(activity);
//                adapter = new VideoHeaderAdapter(activity, bannerView);
//                adapter.setOnItemClickListener(this);
//                recyclerView.setAdapter(adapter);
//                final GridLayoutManager manager = new GridLayoutManager(activity, 2);
//                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                        @Override
//                        public int getSpanSize(int position) {
//                                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
//                        }
//                });
//                recyclerView.setLayoutManager(manager);
//                mPresenter = new HomePresenter(this, activity.getQueue());
//                refreshLayout.post(new Runnable() {
//                        @Override
//                        public void run() {
//                                refreshLayout.setRefreshing(true);
//                                mPresenter.loadHomeData();
//                        }
//                });
//
//        }
//
        @Override
        public void showHomeData(List<Video> banner, List<Video> videos) {
                bannerView.setData(banner, this);
                this.videos=videos;
               setData();
                activity.showHomeData(banner,videos);
        }

        @Override
        public void showError(String msg) {
                ToastUtil.show(getActivity(), msg);
        }
        public void setData(){
                Log.e("size==",videos.size()+"");
                tv_tryone.setText(videos.get(0).getTitle());
                tv02.setText(videos.get(1).getTitle());
                displayImage(im01,videos.get(0).getFace());
                displayImage(im02,videos.get(1).getFace());

                tv11.setText(videos.get(2).getTitle());
                tv12.setText(videos.get(3).getTitle());
                tv13.setText(videos.get(4).getTitle());
                tv14.setText(videos.get(5).getTitle());
                tv15.setText(videos.get(6).getTitle());
                displayImage(im11,videos.get(2).getFace());
                displayImage(im12,videos.get(3).getFace());
                displayImage(im13,videos.get(4).getFace());
                displayImage(im14,videos.get(5).getFace());
                displayImage(im15,videos.get(6).getFace());


                tv21.setText(videos.get(7).getTitle());
                tv22.setText(videos.get(8).getTitle());
                tv23.setText(videos.get(9).getTitle());
                tv24.setText(videos.get(10).getTitle());
                tv25.setText(videos.get(11).getTitle());
                displayImage(im21,videos.get(7).getFace());
                displayImage(im22,videos.get(8).getFace());
                displayImage(im23,videos.get(9).getFace());
                displayImage(im24,videos.get(10).getFace());
                displayImage(im25,videos.get(11).getFace());


                tv31.setText(videos.get(12).getTitle());
                tv32.setText(videos.get(13).getTitle());
                tv33.setText(videos.get(14).getTitle());
                tv34.setText(videos.get(15).getTitle());
                tv35.setText(videos.get(16).getTitle());
                tv36.setText(videos.get(17).getTitle());
                displayImage(im31,videos.get(12).getFace());
                displayImage(im32,videos.get(13).getFace());
                displayImage(im33,videos.get(14).getFace());
                displayImage(im34,videos.get(15).getFace());
                displayImage(im35,videos.get(16).getFace());
                displayImage(im36,videos.get(17).getFace());

                tv41.setText(videos.get(18).getTitle());
                tv42.setText(videos.get(19).getTitle());
                tv43.setText(videos.get(20).getTitle());
                tv44.setText(videos.get(21).getTitle());
                tv45.setText(videos.get(22).getTitle());
                tv46.setText(videos.get(23).getTitle());
                tv47.setText(videos.get(24).getTitle());
                tv48.setText(videos.get(25).getTitle());
                tv49.setText(videos.get(26).getTitle());
                displayImage(im41,videos.get(18).getFace());
                displayImage(im42,videos.get(19).getFace());
                displayImage(im43,videos.get(20).getFace());
                displayImage(im44,videos.get(21).getFace());
                displayImage(im45,videos.get(22).getFace());
                displayImage(im46,videos.get(23).getFace());
                displayImage(im47,videos.get(24).getFace());
                displayImage(im48,videos.get(25).getFace());
                displayImage(im49,videos.get(26).getFace());

        }



        @Override
        public void onBannerClick(int position, Video video) {
                startActivity(VideoActivity.createIntent(getActivity(), video.getId()));
        }

        @Override
        public void displayImage(ImageView imageView, String url) {
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.video_loading, R.mipmap.video_loading);
                Log.e("url==",url);
                activity.getImageLoader().get(url, imageListener);
//                boolean isFileExist = ComTools.isFileExist(url, activity);

//                if (isFileExist) {
//                        Bitmap bitmap = ComTools.getBitmap2(url, activity);
//                        imageView.setImageBitmap(bitmap);
//
//                }else {
////如果有网络的话，再通过vollery调用
//                        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.video_loading, R.mipmap.video_loading);
//                        Log.e("url==",url);
//                        activity.getImageLoader().get(url, imageListener);
//                }
//                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.mipmap.video_loading, R.mipmap.video_loading);
//                Log.e("url==",url);
//                activity.getImageLoader().get(url, imageListener);
        }

        @Override
        public void onClick(View v) {
                switch (v.getId()){
                        case R.id.fisttab:
                                startActivity(ListActivity.createIntent(activity, 2,"亚洲专区"));

                                break;
                        case R.id.secontab:
                                startActivity(ListActivity.createIntent(activity, 3,"欧美专区"));

                                break;
                        case R.id.thtab:
                                startActivity(ListActivity.createIntent(activity, 4,"性感美女"));

                                break;
                        case R.id.fourtab:
                                startActivity(ListActivity.createIntent(activity, 7,"邪恶动漫"));

                                break;

                        case R.id.tryone:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(0).getId()));
                                break;
                        case R.id.trytwo:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(1).getId()));
                                break;
                        case R.id.fistone:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(2).getId()));
                                break;
                        case R.id.fisttwo:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(3).getId()));
                                break;
                        case R.id.fisth:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(4).getId()));
                                break;
                        case R.id.fistf:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(5).getId()));
                                break;
                        case R.id.fistfi:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(6).getId()));
                                break;
                        case R.id.secondone:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(7).getId()));
                                break;
                        case R.id.secondtwo:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(8).getId()));
                                break;
                        case R.id.secondth:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(9).getId()));
                                break;
                        case R.id.secondf:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(10).getId()));
                                break;
                        case R.id.secondfi:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(11).getId()));
                                break;
                        case R.id.thone:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(12).getId()));
                                break;
                        case R.id.thtwo:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(13).getId()));
                                break;
                        case R.id.tht:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(14).getId()));
                                break;
                        case R.id.thtf:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(15).getId()));
                                break;
                        case R.id.thfi:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(16).getId()));
                                break;
                        case R.id.thsix:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(17).getId()));
                                break;
                        case R.id.fourone:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(18).getId()));
                                break;
                        case R.id.fourtwo:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(19).getId()));
                                break;
                        case R.id.fourth:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(20).getId()));
                                break;
                        case R.id.fourf:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(21).getId()));
                                break;
                        case R.id.fourfive:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(22).getId()));
                                break;
                        case R.id.foursix:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(23).getId()));
                                break;
                        case R.id.fourseven:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(24).getId()));
                                break;
                        case R.id.foure:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(25).getId()));
                                break;
                        case R.id.fourn:
                                startActivity(VideoActivity.createIntent(getActivity(), videos.get(26).getId()));
                                break;
                }
        }

//        @Override
//        public void onItemClick(View v, int position) {
//        startActivity(VideoActivity.createIntent(getActivity(), adapter.getItem(position).getId()));
////                startActivity(WappayTest.createIntent(activity, adapter.getItem(position).getId()));
//        }


}
