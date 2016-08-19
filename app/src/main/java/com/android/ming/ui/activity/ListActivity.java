package com.android.ming.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Video;
import com.android.ming.presenter.ListPresenter;
import com.android.ming.ui.adapter.BaseRecyclerAdapter;
import com.android.ming.ui.adapter.VideoAdapter;
import com.android.ming.ui.view.IListView;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;

import java.util.List;

public class ListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, IListView,BaseRecyclerAdapter.OnItemClickListener {
    private static final String CHANNEL_ID = "cid";
    private static final String CHANNEL_TITLE = "title";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ListPresenter presenter;
    private int cid;
    private int currentPage = 1;
    private ProgressDialog myDialog;
    protected AlertDialog tipDialog;

    public static Intent createIntent(Context context, int id, String title) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(CHANNEL_ID, id);
        Log.e("id",id+"");
        Log.e("title","="+title);
        intent.putExtra(CHANNEL_TITLE, title);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        cid = intent.getIntExtra(CHANNEL_ID, 0);
        String title = intent.getStringExtra(CHANNEL_TITLE);
        setUpCommonBackTooblBar(R.id.toolbar, title);


        presenter = new ListPresenter(this, getQueue());
        adapter = new VideoAdapter(this);
        adapter.setOnItemClickListener(this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_orange_light);
        refreshLayout.setOnRefreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        final GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        presenter.loadVideos(cid, currentPage);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
             int    lastVisibleItem = manager.findLastVisibleItemPosition();
                refreshLayout.setRefreshing(false);
                if (lastVisibleItem>=14){
                    if (SPUtil.getInt(ListActivity.this, Consts.SP.VIP) == 0){
                        dialog();
                    }

                }
                Log.e("lass==",lastVisibleItem+"");
                    Log.e("cur",currentPage+"");
            }
        });
    }


    protected void dialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.list_vip_tip_layout, null);
        if (tipDialog==null)
        tipDialog = new AlertDialog.Builder(this).setView(view).create();
        tipDialog.setCanceledOnTouchOutside(false);
        TextView vip1Btn = (TextView) view.findViewById(R.id.vip1Btn);
        vip1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
            }
        });
        TextView vip2Btn = (TextView) view.findViewById(R.id.vip2Btn);
        vip2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipDialog.dismiss();
                PayActivity.createInstance(ListActivity.this, 2);
            }
        });
        tipDialog.show();
         }
    /**
     * 圆形进度条测试..
     */
    public void circle() {
        myDialog = new ProgressDialog(ListActivity.this); // 获取对象
        myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置样式为圆形样式
//                myDialog.setTitle("友情提示"); // 设置进度条的标题信息
        myDialog.setMessage("检查到您还不是VIP会员，值加载前2页的内容，想看更多隐藏内容，请充值vip"); // 设置进度条的提示信息
//                myDialog.setIcon(R.drawable.ic_launcher); // 设置进度条的图标
        myDialog.setIndeterminate(false); // 设置进度条是否为不明确
        myDialog.setCancelable(true); // 设置进度条是否按返回键取消

//                // 为进度条添加确定按钮 ， 并添加单机事件
//                myDialog.setButton("确定", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int which) {
//
//                                myDialog.cancel(); // 撤销进度条
//                        }
//                });
        if (!myDialog.isShowing())
        myDialog.show(); // 显示进度条
    }
    @Override
    public void onRefresh() {
        presenter.loadVideos(cid, currentPage);
    }

    @Override
    public void showVideos(List<Video> videos) {
        Log.e("vide==",videos.size()+"");
        refreshLayout.setRefreshing(false);
        adapter.refreshData(videos);

        if (!refreshLayout.isRefreshing()) {
            Log.e("MainFragment==", currentPage+"==recyclerview到底了");
        }
        currentPage++;

    }

    @Override
    public void showError(String msg) {
        ToastUtil.show(this, msg);
    }

    @Override
    public void onItemClick(View v, int position) {
        startActivity(VideoActivity.createIntent(this, adapter.getItem(position).getId()));
    }
}
