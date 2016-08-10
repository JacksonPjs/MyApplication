package com.android.ming.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.android.ming.R;
import com.android.ming.utils.AppUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splash = (ImageView) findViewById(R.id.splash);
        Animation scaleAnimation = new ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);// 播放完毕停在最后一帧
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        splash.startAnimation(scaleAnimation);
//        splash.setBackgroundDrawable(this.getResources().getDrawable(R.mipmap.splash));
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                finish();

        AppUtil.log(this);
    }
}
