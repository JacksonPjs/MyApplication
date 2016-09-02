package com.android.ming.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.ming.R;
import com.android.ming.bean.Pay;
import com.android.ming.utils.ToastUtil;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by YO on 2016/9/1.
 */
public class webActivity extends Activity {
    WebView webView;
    String url,traceno;
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
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.setWebChromeClient(new MyWebChromeClient());
        /*
         * DemoJavaScriptInterface类为js调用android服务器端提供接口
         * android 作为DemoJavaScriptInterface类的客户端接口被js调用
         * 调用的具体方法在DemoJavaScriptInterface中定义：
         * 例如该实例中的clickOnAndroid
         */
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "androd");
        webView.loadUrl(url);


//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                webView.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
////                mProgressDialog.show();
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
////                mProgressDialog.hide();
//                isShow=true;
//            }
//        });
//        webView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.show(webActivity.this,"webView onclick");
//
//            }
//        });
        //"message":"ÏµÍ³Òì³£,Î´ÖªµÄ½»Ò×ÀàÐÍ[pay.weixin.wappay]","respCode":"99"
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://112.74.230.8:8081/posp-api/qrcodeQuery",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.e("ressu",response);
//                            Gson gson=new Gson();
////
//                            Pay pay=gson.fromJson(response,new TypeToken<Pay>(){}.getType());
//                            if (pay.getRespCode().equals("00")) {
//                                Log.e("pay==", pay.getBarCode());
//                                Uri uri = Uri.parse(pay.getBarCode());
//
////                                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                                Intent intent=new Intent(webActivity.this,webActivity.class);
//                                intent.putExtra("url",pay.getBarCode());
//                                intent.putExtra("traceno",pay.getTraceno());
//                                startActivity(intent);
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("res",error.toString());
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    //在这里设置需要post的参数
//                    Map<String, String> param = new HashMap<>();
////                                                int payType=2;
////                                                if (type==ALIPAY){
////                                                        payType=1;
////                                                }
//                    try {
//                        param.put("merchno", "688440357220001");
//
//                        param.put("traceno", "4002022001201609022877052915");
////                        param.put("refno", "800000124682");
//
//
//                        param.put("signature", signature(param, "53DDC95577CAC4E58E3B579842EA3188"));
//                        Log.e("request:", param+"");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return param;
//                }
//            };
//
//        requestQueue.add(stringRequest);
    }
        final class DemoJavaScriptInterface {
            DemoJavaScriptInterface() {}

            /**
             * 该方法被浏览器端调用
             */
            public void clickOnAndroid() {
                mHandler.post(new Runnable() {
                    public void run() {
                        //调用js中的onJsAndroid方法
                        webView.loadUrl("javascript:onJsAndroid()");
                    }
                });
            }
        }



    /**
     * 继承WebChromeClient类
     * 对js弹出框时间进行处理
     *
     */
    final class MyWebChromeClient extends WebChromeClient {

        /**
         * 处理alert弹出框
         */
        @Override
        public boolean onJsAlert(WebView view,String url,
                                 String message,JsResult result) {
            Log.d(LOG_TAG,"onJsAlert:"+message);
//                    mReusultText.setText("Alert:"+message);
            //对alert的简单封装
            new AlertDialog.Builder(webActivity.this).
                    setTitle("Alert").setMessage(message).setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //TODO
                        }
                    }).create().show();
            result.confirm();
            return true;
        }

        /**
         * 处理confirm弹出框
         */
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            Log.d(LOG_TAG, "onJsConfirm:"+message);
//            mReusultText.setText("Confirm:"+message);
            result.confirm();
            return super.onJsConfirm(view, url, message, result);
        }

        /**
         * 处理prompt弹出框
         */
        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            Log.d(LOG_TAG,"onJsPrompt:"+message);
//            mReusultText.setText("Prompt input is :"+message);
            result.confirm();
            return super.onJsPrompt(view, url, message, message, result);
        }
    }

//分类: Android
    @Override
    protected void onRestart() {
        super.onRestart();
        ToastUtil.show(this,"onrestart");
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        ToastUtil.show(this,"onresume");
////        if (isShow){
////            this.finish();
////            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
////            StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://112.74.230.8:8081/posp-api/wapPay",
////                    new Response.Listener<String>() {
////                        @Override
////                        public void onResponse(String response) {
////                            Log.e("ressu",response);
////                            Gson gson=new Gson();
//////
////                            Pay pay=gson.fromJson(response,new TypeToken<Pay>(){}.getType());
////                            if (pay.getRespCode().equals("00")) {
////                                Log.e("pay==", pay.getBarCode());
////                                Uri uri = Uri.parse(pay.getBarCode());
////
//////                                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
////                                Intent intent=new Intent(webActivity.this,webActivity.class);
////                                intent.putExtra("url",pay.getBarCode());
////                                intent.putExtra("traceno",pay.getTraceno());
////                                startActivity(intent);
////                            }
////                        }
////                    }, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////                    Log.e("res",error.toString());
////                }
////            }) {
////                @Override
////                protected Map<String, String> getParams() {
////                    //在这里设置需要post的参数
////                    Map<String, String> param = new HashMap<>();
//////                                                int payType=2;
//////                                                if (type==ALIPAY){
//////                                                        payType=1;
//////                                                }
////                    try {
////                        param.put("merchno", "688440357220001");
////
////                        param.put("traceno", traceno);
////                        param.put("notifyUrl", "http://gf-info.cn:8085/result.jsp");
////
////
////                        param.put("signature", signature(param, "53DDC95577CAC4E58E3B579842EA3188"));
////
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
////                    return param;
////                }
////            };
////            requestQueue.add(stringRequest);
////        }
//
//    }
    protected static String signature(Map<String, String> param, String keyValue)
            throws Exception {
        Set<String> set = param.keySet();
        List<String> keys = new ArrayList<String>(set);
        Collections.sort(keys);
        boolean start = true;
        StringBuffer sb = new StringBuffer();
        for (String key : keys) {
            String value = param.get(key);
            if (value != null && !value.trim().equals("")
                    && !"signature".equalsIgnoreCase(key)) {
                if (!start) {
                    sb.append("&");
                }
                sb.append(key + "=" + value);
                start = false;
            }
        }
        String CHARSET = "GBK";
        sb.append("&" + keyValue);
        String src = sb.toString();
        System.out.println("签名数据:" + src);
        String result= new String(Hex.encodeHex(DigestUtils.md5(src.getBytes(CHARSET))));
//		String result = DigestUtils.md5Hex(src.getBytes(CHARSET)).toUpperCase();
        System.out.println("签名结果:" + result);
        return result;
    }
}
