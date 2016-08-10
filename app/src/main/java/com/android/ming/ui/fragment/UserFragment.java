package com.android.ming.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Setting;
import com.android.ming.presenter.UserPresenter;
import com.android.ming.ui.activity.ActiveActivity;
import com.android.ming.ui.activity.PayActivity;
import com.android.ming.ui.activity.ProtocolActivity;
import com.android.ming.ui.adapter.SettingAdapter;
import com.android.ming.ui.recyclerview.RecyclerViewDivider;
import com.android.ming.ui.view.IUserView;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;

import java.util.List;

/**
 * Created by KingYang on 2016/3/9.
 * E-Mail: admin@kingyang.cn
 */
public class UserFragment extends RefreshableFragment implements IUserView, SettingAdapter.OnItemClickListener {
    private SettingAdapter adapter;
    private UserPresenter mPresenter;


    @Override
    public void onFragmentCreate() {
        adapter = new SettingAdapter(activity);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(new RecyclerViewDivider(activity, LinearLayoutManager.VERTICAL, R.drawable.divider_trans));
        mPresenter = new UserPresenter(this, activity);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                mPresenter.loadInfo();
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.loadInfo();
    }

    @Override
    public void showUserInfo(List<Setting> settings) {
        refreshLayout.setRefreshing(false);
        adapter.refreshData(settings);
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (position) {
            case 1:
                if (SPUtil.getInt(activity, Consts.SP.VIP) > 0) {
                    ToastUtil.show(activity, "已开通会员服务");
                } else {
                    PayActivity.createInstance(activity, 1);
                }
                break;
            case 2:
                startActivity(new Intent(activity, ActiveActivity.class));
                break;
            case 3:
                String text = String.format(activity.getResources().getString(R.string.contact_text), Consts.APP.CONTACT_QQ);
                new AlertDialog.Builder(activity).setMessage(text).setPositiveButton(R.string.contact, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=" + Consts.APP.CONTACT_QQ));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    ToastUtil.show(activity, "请安装QQ");
                                }
                            }
                        }

                ).setNegativeButton(android.R.string.cancel, null).show();
                break;
            case 4:
                startActivity(new Intent(activity, ProtocolActivity.class));
                break;
            case 5:
                ToastUtil.show(activity, "当前已是最新版本");
        }
    }
}
