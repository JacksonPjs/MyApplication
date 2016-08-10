package com.android.ming.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.ming.app.Consts;
import com.android.ming.ui.activity.BaseActivity;
import com.android.ming.utils.volley.RespCallback;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KingYang on 2016/4/21.
 * E-Mail: admin@kingyang.cn
 */
public class AppUtil {
    public static String getMetaData(Context context, String key) {
        try {
            return context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String createUid(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        return md5(androidId + deviceId);
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (Exception e) {
            return "";
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString().substring(8, 24);
    }

    public static void log(final BaseActivity activity) {
        if (SPUtil.getBoolean(activity, Consts.SP.LOGED)) {
            return;
        }
        RespCallback<String> callback = new RespCallback<String>() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                if ("success".equals(s)) {
                    SPUtil.putBoolean(activity, Consts.SP.LOGED, true);
                }
            }
        };
        StringRequest request = new StringRequest(Request.Method.POST, Consts.URL.ORDER_LOG, callback, callback) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", SPUtil.getString(activity, Consts.SP.UID));
                params.put("channel", getMetaData(activity, Consts.APP.CHANNEL_NAME));
                return params;
            }
        };
        activity.getQueue().add(request);
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context
     * @param className
     *            某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

}
