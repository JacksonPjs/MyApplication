package com.android.ming.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ming.R;
import com.android.ming.bean.Video;
import com.android.ming.ui.fragment.VideoFragment;
import com.android.ming.utils.ViewFindUtils;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.List;

public class SimpleImageBanner extends BaseBanner<Integer, SimpleImageBanner> {
    private BannerViewListener mListener;
    List<Video> videos;

    public SimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onTitleSlect(TextView tv, int position) {
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate = View.inflate(context, R.layout.adapter_simple_image, null);
        ImageView iv = ViewFindUtils.find(inflate, R.id.iv);

        Integer i = list.get(position);
//        int itemWidth = dm.widthPixels;
//        int itemHeight = (int) (itemWidth * 360 * 1.0f / 640);
//        iv.setScaleType(ImageView.ScaleType.FIT_XY);
//        iv.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, itemHeight));

//        iv.setImageResource(i);
        mListener.displayImage(iv,videos.get(i).getFace());
        return inflate;
    }

    public void setVideo(List<Video> video,BannerViewListener listener){
        videos=video;
        mListener=listener;
    }
//    private RoundCornerIndicaor indicator;
public interface BannerViewListener {

    void displayImage(ImageView imageView, String url);
}
    @Override
    public View onCreateIndicator() {
//        indicator = new RoundCornerIndicaor(context);
//        indicator.setViewPager(vp, list.size());
//        return indicator;
        return null;
    }

    @Override
    public void setCurrentIndicator(int i) {
        //  indicator.setCurrentItem(i);
    }


}
