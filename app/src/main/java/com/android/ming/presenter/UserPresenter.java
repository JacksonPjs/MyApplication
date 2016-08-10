package com.android.ming.presenter;

import android.content.Context;

import com.android.ming.model.UserModel;
import com.android.ming.ui.view.IUserView;

/**
 * Created by KingYang on 16/3/26.
 * E-Mail: admin@kingyang.cn
 */
public class UserPresenter {
    private IUserView view;
    private UserModel userModel;
    private Context context;

    public UserPresenter(IUserView view, Context context) {
        this.userModel = new UserModel();
        this.view = view;
        this.context = context;
    }

    public void loadInfo() {
        view.showUserInfo(userModel.getUserInfo(context));
    }
}
