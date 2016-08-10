package com.android.ming.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.ui.adapter.FragmentAdapter;
import com.android.ming.ui.fragment.ChannelFragment;
import com.android.ming.ui.fragment.HomeFragment;
import com.android.ming.ui.fragment.LiveFragment;
import com.android.ming.ui.fragment.PicFragment;
import com.android.ming.ui.fragment.UserFragment;
import com.android.ming.ui.view.AnimationToast;
import com.android.ming.ui.view.ToastCommom;
import com.android.ming.utils.AppUtil;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private TextView tabHome, tabChannel,tabPic, tabStar, tabUser;
    private Toolbar toolbar;
    private long lastBack;
    private AlertDialog pay2Dialog;
    private  AnimationToast toastCommom;
    Random random=new Random();
    Handler handler = new Handler();
    int ran=random.nextInt(5000) + 5000;
    int peo=ran;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            int n =random.nextInt(5)+1;
            int m=random.nextInt(10)+1;
            if (AppUtil.isForeground(MainActivity.this,"com.android.ming.ui.activity.MainActivity"))
            handler.postDelayed(this, m*1000);

            peo=peo+n;
            toastCommom.makeText(
                    MainActivity.this,
                    "第"+peo+"位永久会员充值成功",
                    AnimationToast.LENGTH_LONG,
                    MainActivity.this.getWindow().getDecorView()
            ).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = setToolBarTitle(R.id.toolbar, getTitle());
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setLogo(R.mipmap.ic_launcher);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new ChannelFragment());
        fragments.add(new LiveFragment());
        fragments.add(new UserFragment());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);

        tabHome = (TextView) findViewById(R.id.tabHome);
        tabChannel = (TextView) findViewById(R.id.tabChannel);
        tabStar = (TextView) findViewById(R.id.tabStar);
        tabUser = (TextView) findViewById(R.id.tabUser);
        tabHome.setOnClickListener(this);
        tabChannel.setOnClickListener(this);
        tabStar.setOnClickListener(this);
        tabUser.setOnClickListener(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay2, null);
        pay2Dialog = new AlertDialog.Builder(this).setView(view).create();
        pay2Dialog.setCanceledOnTouchOutside(false);
        final TextView pay2Btn = (TextView) view.findViewById(R.id.pay2Btn);
        pay2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay2Dialog.dismiss();
                PayActivity.createInstance(MainActivity.this, 3);
            }
        });
        //判断app启动
        if (!SPUtil.getBoolean(this,Consts.SP.IS_FIRST)){
            SPUtil.putBoolean(this, Consts.SP.IS_FIRST, true);
        }
        handler.postDelayed(runnable, 2000);

    }

    @Override
    public void onClick(View v) {
        if (v == tabHome) {
            viewPager.setCurrentItem(0);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setLogo(R.mipmap.ic_launcher);
            handler.postDelayed(runnable, 2000);
        } else if (v == tabChannel) {
            viewPager.setCurrentItem(1);
            toolbar.setTitle(R.string.channel);
            toolbar.setLogo(null);
            handler.removeCallbacks(runnable);

        }
        else if (v == tabStar) {
            viewPager.setCurrentItem(2);
            toolbar.setTitle(R.string.star);
            toolbar.setLogo(null);
            handler.removeCallbacks(runnable);

        } else if (v == tabUser) {
            viewPager.setCurrentItem(3);
            toolbar.setLogo(null);
            toolbar.setTitle(R.string.user);
            handler.removeCallbacks(runnable);

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 设置DrawableTop
        Drawable home = getResources().getDrawable(position == 0 ? R.mipmap.tab_home_focus : R.mipmap.tab_home_normal);
        home.setBounds(0, 0, home.getMinimumWidth(), home.getMinimumHeight());
        tabHome.setCompoundDrawables(null, home, null, null);
        tabHome.setTextColor(position == 0 ? getResources().getColor(R.color.tab_focus) : Color.WHITE);

        Drawable channel = getResources().getDrawable(position == 1 ? R.mipmap.tab_channel_focus : R.mipmap.tab_channel_normal);
        channel.setBounds(0, 0, channel.getMinimumWidth(), channel.getMinimumHeight());
        tabChannel.setCompoundDrawables(null, channel, null, null);
        tabChannel.setTextColor(position == 1 ? getResources().getColor(R.color.tab_focus) : Color.WHITE);


        Drawable star = getResources().getDrawable(position == 2 ? R.mipmap.tab_star_focus : R.mipmap.tab_star_normal);
        star.setBounds(0, 0, star.getMinimumWidth(), star.getMinimumHeight());
        tabStar.setCompoundDrawables(null, star, null, null);
        tabStar.setTextColor(position == 2 ? getResources().getColor(R.color.tab_focus) : Color.WHITE);


        Drawable user = getResources().getDrawable(position == 3 ? R.mipmap.tab_user_focus : R.mipmap.tab_user_normal);
        user.setBounds(0, 0, user.getMinimumWidth(), user.getMinimumHeight());
        tabUser.setCompoundDrawables(null, user, null, null);
        tabUser.setTextColor(position == 3 ? getResources().getColor(R.color.tab_focus) : Color.WHITE);


    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void showPay1Dialog() {
        PayActivity.createInstance(this, 1);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBack < 1000) {
            if (SPUtil.getInt(this, Consts.SP.VIP) > 0) {
                super.onBackPressed();
            } else {// 退出时没有付费则弹出特价永久会员
//                pay2Dialog.show();
                PayActivity.createInstance(this, 3);
            }
        } else {
            ToastUtil.show(this, "再按一次退出应用");
        }
        lastBack = System.currentTimeMillis();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (viewPager.getCurrentItem()==0)
        handler.postDelayed(runnable,2000);
    }
}
