package com.android.ming.ui.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

/**
 * Created by YO on 2016/9/12.
 */
public class MyDialog {
    private static ProgressDialog myDialog;
    public static ProgressDialog getMyDialog(Context context) {
        myDialog = new ProgressDialog(context);

        return myDialog;
    }

    public static void circle(Context context) {
        myDialog=getMyDialog(context);
        myDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // 设置样式为圆形样式
//                myDialog.setTitle("友情提示"); // 设置进度条的标题信息
        myDialog.setMessage("请稍后..."); // 设置进度条的提示信息
//                myDialog.setIcon(R.drawable.ic_launcher); // 设置进度条的图标
        myDialog.setIndeterminate(false); // 设置进度条是否为不明确
        myDialog.setCancelable(false); // 设置进度条是否按返回键取消

//                // 为进度条添加确定按钮 ， 并添加单机事件
//                myDialog.setButton("确定", new DialogInterface.OnClickListener() {
//
//                        public void onClick(DialogInterface dialog, int which) {
//
//                                myDialog.cancel(); // 撤销进度条
//                        }
//                });

        myDialog.show(); // 显示进度条
    }

    public  static  void hide(){
        myDialog.dismiss();
    }
}
