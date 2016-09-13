package com.android.ming.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
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
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress==100){
////
//                    PayActivity.ProgressDialogDismiss();
//
//                }
//            }
//        });
        isShow=false;
        new Thread(){
            @Override
            public void run() {
                super.run();
                String html =
                 getHtmlString(url);
//                Log.e("tesoo",html.toString());
                Document document=Jsoup.parse(html);
              Element element= document.getElementById("cli");
                String s=element.attr("href");
             String ur=   URLDecoder.decode(s);
                Log.e("wtf",document.toString());
                Log.e("wtfs",s);
                Log.e("wtfu",ur);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(ur);
                intent.setData(content_url);
                startActivity(intent);
            }
        }.start();
//                    String html =
//                            getHtmlString("http://blog.csdn.net/u010142437");
//                    // 再使用第一种方式
//                    Log.e("tesoo",html);
//        Document doc = Jsoup.parse(html);
////         handler.sendEmptyMessage(0);

//    handler.sendEmptyMessage(0);
//        webView.loadUrl(url);




    }


    /**
     * 使用URLConnection根据url读取html源代码
     *
     * @param urlString
     * @return
     */
    private String getHtmlString(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection ucon = url.openConnection();
            InputStream instr = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(instr);
            ByteArrayBuffer bau = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
                bau.append((byte) current);
            }
            return EncodingUtils.getString(bau.toByteArray(), "utf_8");
        } catch (Exception e) {
            return "";
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                URL url1 = new URL(url);
                InputStream in = url1.openStream();
                byte buff[] = new byte[1024];
                ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
                FileOutputStream out = null;
                do {
                    int numread = in.read(buff);
                    if (numread <= 0) {
                        break;
                    }
                    fromFile.write(buff, 0, numread);
                } while (true);
                String wholeJS = fromFile.toString();
                Log.e("wholejs",wholeJS);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

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
