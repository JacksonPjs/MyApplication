package com.android.ming.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Bean;
import com.android.ming.bean.Channel;
import com.android.ming.bean.Video;
import com.android.ming.ui.adapter.SearchAdapter;
import com.android.ming.ui.adapter.SearchGridViewAdapter;
import com.android.ming.ui.widget.SearchView;
import com.android.ming.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;



public class SearchActivity extends Activity implements SearchView.SearchViewListener {

    /**
     * 搜索结果列表view
     */
//    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private SearchAdapter hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private SearchAdapter autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
//    private SearchAdapter resultAdapter;

    private List<Video> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<Video> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<Video> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    List<Video> videos=new ArrayList<>();
    List<Channel> cids=new ArrayList<>();
    GridView gridView;
    SearchGridViewAdapter adapter;
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_main);
        Intent intent=getIntent();
        videos= (List<Video>) intent.getSerializableExtra("videos");
        cids= (List<Channel>) intent.getSerializableExtra("channels");

        initData();
        initViews();
    }

    /**
     * 初始化视图
     */
    private void initViews() {
//        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        gridView= (GridView) findViewById(R.id.main_lv_search_results);
        adapter=new SearchGridViewAdapter(this,cids);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(ListActivity.createIntent(SearchActivity.this, cids.get(position).getId(), cids.get(position).getTitle()));
            }
        });
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter,videos);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter,videos);

//        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                Toast.makeText(SearchActivity.this, position + "", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
//        getResultData(null);
    }

    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        dbData=videos;
//        for (int i = 0; i < size; i++) {
//            dbData.add(new Bean(R.mipmap.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
//        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter =new SearchAdapter(this,videos,R.layout.item_bean_list);
    }

    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < videos.size()
                    && count < hintSize; i++) {
                if (videos.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(videos.get(i));
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new SearchAdapter(this,autoCompleteData,R.layout.item_bean_list);
        } else {
            searchView.setChange(autoCompleteData);
//            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }

//        if (resultAdapter == null) {
//            resultAdapter = new SearchAdapter(this, resultData, R.layout.item_bean_list);
//        } else {
//            resultAdapter.notifyDataSetChanged();
//        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param video
     */
    @Override
    public void onSearch(Video video) {
        //更新result数据
//        getResultData(text.getTitle());
//        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
//        if (lvResults.getAdapter() == null) {
//            //获取搜索数据 设置适配器
//            lvResults.setAdapter(resultAdapter);
//        } else {
//            //更新搜索数据
//            resultAdapter.notifyDataSetChanged();

//        }
        if ( SPUtil.getInt(this, Consts.SP.VIP) > 0) {// 如果为免费视频或者已付费，即可完成搜索
            startActivity(VideoActivity.createIntent(this,video.getId()));
            Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
        } else {

            SearchFailActivity.createInstance(SearchActivity.this, video.getTitle());
        }



    }

}
