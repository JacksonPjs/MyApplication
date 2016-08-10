package com.android.ming.presenter;

import com.android.ming.bean.Video;
import com.android.ming.model.VideoModel;
import com.android.ming.ui.view.ILiveView;
import com.android.ming.ui.view.IPicView;
import com.android.ming.utils.volley.RespCallback;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by YO on 2016/8/9.
 */
public class PicPresenter {
        private IPicView view;
        private VideoModel videoModel;
        private Gson gson;
        public PicPresenter(IPicView view, RequestQueue queue) {
                this.videoModel = new VideoModel(queue);
                this.view = view;
                gson = new Gson();
        }

        public void loadPic() {
                videoModel.getByChannel(100, new RespCallback<String>() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                                view.showError("连接服务器失败");
                        }

                        @Override
                        public void onResponse(String s) {
                                try {

                                        List<Video> videos = gson.fromJson(s, new TypeToken<List<Video>>() {
                                        }.getType());
                                        view.showPics(videos);
                                } catch (Exception e) {
                                        view.showError("解析数据出错");
                                }
                        }
                });
        }
}
