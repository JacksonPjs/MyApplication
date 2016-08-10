package com.android.ming.ui.view;

import com.android.ming.bean.Channel;

import java.util.List;

/**
 * Created by KingYang on 16/3/24.
 * E-Mail: admin@kingyang.cn
 */
public interface IChannelView {
    void showList(List<Channel> channels);

    void showError(String msg);
}
