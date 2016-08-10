package com.android.ming.ui.view;

import android.content.Context;
import android.text.Annotation;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ming.R;

/**
 * Created by YO on 2016/8/9.
 */
public class ToastCommom {


        private static ToastCommom toastCommom;

        private Toast toast;

        private ToastCommom(){
        }

        public static ToastCommom createToastConfig(){
                if (toastCommom==null) {
                        toastCommom = new ToastCommom();
                }
                return toastCommom;
        }

        /**
         * 显示Toast
         * @param context
         * @param root
         * @param tvString
         */

        public void ToastShow(Context context, ViewGroup root, String tvString){
                View layout = LayoutInflater.from(context).inflate(R.layout.toast_xml,root);
                TextView text = (TextView) layout.findViewById(R.id.text);
                ImageView mImageView = (ImageView) layout.findViewById(R.id.iv);
                mImageView.setBackgroundResource(R.mipmap.pay_3rd_select_p);
                text.setText(tvString);
                text.setTextColor(context.getResources().getColor(R.color.white));
                toast = new Toast(context);
                toast.setGravity(Gravity.TOP, 0, 250);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                Animation animation=new TranslateAnimation(0, 150,0, 0);
                animation.setDuration(2000);//设置动画持续时间

                toast.show();
        }
        public void dissmiss(){
                toast.cancel();
        }

}
