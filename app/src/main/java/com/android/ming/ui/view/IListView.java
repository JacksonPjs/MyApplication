package com.android.ming.ui.view;

import com.android.ming.bean.Video;

import java.util.List;

/**
 * Created by KingYang on 2016/4/29.
 * E-Mail: admin@kingyang.cn
 */
public interface IListView {
    void showVideos(List<Video> videos);
    void showError(String msg);
}
