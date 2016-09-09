package com.android.ming.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Pay;
import com.android.ming.bean.PayResult;
import com.android.ming.utils.AppUtil;
import com.android.ming.utils.ToastUtil;
import com.android.ming.utils.WapPayUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by YO on 2016/9/1.
 */
public class webActivity extends AppCompatActivity {
    WebView webView;
    String url,traceno,refno,money;
    private ProgressDialog mProgressDialog;
    boolean isShow=false;
    private Handler mHandler = new Handler();
    private static final String LOG_TAG = "webActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = (WebView) findViewById(R.id.webview);
        mProgressDialog = new ProgressDialog(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        traceno = intent.getStringExtra("traceno");
        refno = intent.getStringExtra("refno");
        money = intent.getStringExtra("money");
        webView.setBackgroundColor(0);
        WebSettings webSettings = webView.getSettings();
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setSupportZoom(false);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//
////        webView.setWebChromeClient(new MyWebChromeClient());
//        webView.setWebViewClient(new WebViewClient(){
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url1) {
////                webView.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                mProgressDialog.show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                mProgressDialog.hide();
//            }
//        });
        /*
         * DemoJavaScriptInterface类为js调用android服务器端提供接口
         * android 作为DemoJavaScriptInterface类的客户端接口被js调用
         * 调用的具体方法在DemoJavaScriptInterface中定义：
         * 例如该实例中的clickOnAndroid
         */
        isShow=false;
//        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "androd");
//        webView.loadUrl("https://www.baidu.com/");
        webView.loadUrl(url);




    }
//        final class DemoJavaScriptInterface {
//            DemoJavaScriptInterface() {
//            }
//
//            /**
//             * 该方法被浏览器端调用
//             */
//            public void clickOnAndroid() {
//                mHandler.post(new Runnable() {
//                    public void run() {
//                        //调用js中的onJsAndroid方法
//                        webView.loadUrl("javascript:onJsAndroid()");
//                    }
//                });
//            }
//        }
//
//
//
//    /**
//     * 继承WebChromeClient类
//     * 对js弹出框时间进行处理
//     *
//     */
//    final class MyWebChromeClient extends WebChromeClient {
//
//        /**
//         * 处理alert弹出框
//         */
//        @Override
//        public boolean onJsAlert(WebView view,String url,
//                                 String message,JsResult result) {
//            Log.d(LOG_TAG,"onJsAlert:"+message);
////                    mReusultText.setText("Alert:"+message);
//            //对alert的简单封装
//            new AlertDialog.Builder(webActivity.this).
//                    setTitle("Alert").setMessage(message).setPositiveButton("OK",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            //TODO
//                        }
//                    }).create().show();
//            result.confirm();
//            return true;
//        }
//
//        /**
//         * 处理confirm弹出框
//         */
//        @Override
//        public boolean onJsConfirm(WebView view, String url, String message,
//                                   JsResult result) {
//            Log.d(LOG_TAG, "onJsConfirm:"+message);
////            mReusultText.setText("Confirm:"+message);
//            result.confirm();
//            return super.onJsConfirm(view, url, message, result);
//        }
//
//        /**
//         * 处理prompt弹出框
//         */
//        @Override
//        public boolean onJsPrompt(WebView view, String url, String message,
//                                  String defaultValue, JsPromptResult result) {
//            Log.d(LOG_TAG,"onJsPrompt:"+message);
////            mReusultText.setText("Prompt input is :"+message);
//            result.confirm();
//            return super.onJsPrompt(view, url, message, message, result);
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        isShow=true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShow){
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.WapPay.QUERY,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("resQUERY",response);
                            Gson gson=new Gson();
////
                            final PayResult pay=gson.fromJson(response,new TypeToken<PayResult>(){}.getType());
                            if (pay.getRespCode().equals("1")) {
//
                                webView.loadUrl("");
                                Intent aintent = new Intent(webActivity.this, PayActivity.class);
                               setResult(RESULT_OK,aintent); //这理有2个参数(int resultCode, Intent intent)

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.URL.NOTIFY_SS,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        //在这里设置需要post的参数
                                        Map<String, String> map = new HashMap<>();
                                        String channel = AppUtil.getMetaData(webActivity.this, "UMENG_CHANNEL");
                                        map.put("channel", channel);
                                        map.put("wft_orderid", pay.getChannelOrderno());
                                        map.put("wft_fee", money + "");
                                        map.put("trade_type", 0 + "");
                                        return map;
                                    }
                                };

                                requestQueue.add(stringRequest);
                                finish();
                            }else {
                                webView.loadUrl("");
                                Intent aintent = new Intent(webActivity.this, PayActivity.class);
                                setResult(RESULT_CANCELED,aintent); //这理有2个参数(int resultCode, Intent intent)
                                finish();
                            }
//                            else if (pay.getRespCode().equals("0")){
//                                ToastUtil.show(webActivity.this,"未支付");
//                            }else if (pay.getRespCode().equals("2")){
//                                ToastUtil.show(webActivity.this,"支付失败");
//                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("res",error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    //在这里设置需要post的参数
                    Map<String, String> param = new HashMap<>();
                    try {
                        param.put("merchno", Consts.WapPay.MCH_ID);

                        param.put("traceno", traceno);
                        param.put("refno", refno);

                        param.put("signature", WapPayUtils.signature(param, Consts.WapPay.SIGN_KEY));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }

    }

}
