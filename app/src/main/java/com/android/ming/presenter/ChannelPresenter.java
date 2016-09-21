package com.android.ming.presenter;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.android.ming.bean.Channel;
import com.android.ming.model.ChannelModel;
import com.android.ming.ui.view.IChannelView;
import com.android.ming.utils.volley.RespCallback;

import java.util.List;

/**
 * Created by KingYang on 16/3/24.
 * E-Mail: admin@kingyang.cn
 */
public class ChannelPresenter {
    private IChannelView view;
    private ChannelModel channelModel;
    private Gson gson;

    public ChannelPresenter(IChannelView view, RequestQueue queue) {
        this.channelModel = new ChannelModel(queue);
        this.view = view;
        gson = new Gson();
    }

    public void loadList() {
        channelModel.getList(new RespCallback<String>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.showError("连接服务器失败");
            }

            @Override
            public void onResponse(String s) {
                try {
                    List<Channel> channels = gson.fromJson(s, new TypeToken<List<Channel>>() {
                    }.getType());
                    for (int i=0;i<channels.size();i++){
                        Channel channel=channels.get(i);
                        if (channel.getId()==102){
                            channels.remove(i);
                        }
                    }
                    view.showList(channels);
                } catch (Exception e) {
                    view.showError("解析数据出错");
                }
            }
        });
    }

}
