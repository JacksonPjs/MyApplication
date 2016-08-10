package com.android.ming.ui.view;

import com.android.ming.bean.Video;

import java.util.List;

/**
 * Created by YO on 2016/8/9.
 */
public interface IPicView {
        void showPics(List<Video> lives);

        void showError(String msg);


}
