package com.android.ming.presenter;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.android.ming.bean.Video;
import com.android.ming.model.VideoModel;
import com.android.ming.ui.view.IHomeView;
import com.android.ming.utils.volley.RespCallback;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by KingYang on 16/3/23.
 * E-Mail: admin@kingyang.cn
 */
public class HomePresenter {
    private IHomeView view;
    private VideoModel videoModel;
    private Gson gson;

    public HomePresenter(IHomeView view, RequestQueue queue) {
        this.view = view;
        videoModel = new VideoModel(queue);
        gson = new Gson();
    }

    public void loadHomeData() {
        videoModel.getHomeData(new RespCallback<String>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.showError("连接服务器失败");
            }

            @Override
            public void onResponse(String s) {
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject json = parser.parse(s).getAsJsonObject();
                    Type type = new TypeToken<List<Video>>() {
                    }.getType();
                    List<Video> banner = gson.fromJson(json.get("banner"), type);
                    List<Video> videos = gson.fromJson(json.get("videos"), type);
                    view.showHomeData(banner, videos);
                } catch (Exception e) {
                    view.showError("解析数据出错");
                }

            }
        });
    }
}
