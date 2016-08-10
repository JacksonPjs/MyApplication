package com.android.ming.presenter;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.android.ming.bean.Comment;
import com.android.ming.bean.Video;
import com.android.ming.model.VideoModel;
import com.android.ming.ui.view.IVideoView;
import com.android.ming.utils.volley.RespCallback;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by KingYang on 16/3/25.
 * E-Mail: admin@kingyang.cn
 */
public class VideoPresenter {
    private IVideoView view;
    private VideoModel videoModel;
    private Gson gson;

    public VideoPresenter(IVideoView view, RequestQueue queue) {
        this.videoModel = new VideoModel(queue);
        this.view = view;
        gson = new Gson();
    }

    public void loadVideoAndComments(int vid) {
        videoModel.getVideoInfo(vid, new RespCallback<String>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.showError("连接服务器失败");
            }

            @Override
            public void onResponse(String s) {
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject json = parser.parse(s).getAsJsonObject();
                    Type type = new TypeToken<List<Comment>>() {
                    }.getType();
                    Video video = gson.fromJson(json.get("video"), Video.class);
                    List<Comment> comments = gson.fromJson(json.get("comments"), type);
                    view.showVideoAndComments(video, comments);
                } catch (Exception e) {
                    view.showError("解析数据出错");
                }
            }
        });
    }

}
