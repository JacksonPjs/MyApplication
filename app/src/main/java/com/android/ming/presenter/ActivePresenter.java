package com.android.ming.presenter;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.ming.model.ActiveModel;
import com.android.ming.ui.view.IActiveView;
import com.android.ming.utils.volley.RespCallback;

/**
 * Created by KingYang on 2016/4/30.
 * E-Mail: admin@kingyang.cn
 */
public class ActivePresenter {
    private IActiveView view;
    private ActiveModel activeModel;

    public ActivePresenter(IActiveView view, RequestQueue queue) {
        this.view = view;
        this.activeModel = new ActiveModel(queue);
    }

    public void activeVip(String code){
        view.onActiving();
        activeModel.activeVip(code, new RespCallback<String>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                view.showError("链接服务器失败");
            }

            @Override
            public void onResponse(String s) {
                view.showResult(s);
            }
        });
    }
}
