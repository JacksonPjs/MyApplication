package com.android.ming.utils;

import android.content.Context;
import android.util.Log;

import com.android.ming.app.Consts;
import com.android.ming.ui.activity.BaseActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YO on 2016/7/21.
 */
public class HttpUtils {
    final String TAG="httputils";
    public static void DoPost(Context context, final BaseActivity activity) {



        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Consts.NewPay.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("", "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Version", "1.0");
                params.put("InputCharset", "UTF-8");
                params.put("SignType", "?");
                params.put("CustomerNo", Consts.NewPay.CustomerNo);
                params.put("GoodNo", Consts.NewPay.GoodNo);
                params.put("ProductNo", Consts.NewPay.ProductNo);
                //
                //
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

//    public  static  void post(){
//
//        CloseableHttpResponse response = null;
//        CloseableHttpClient client = null;
//        String res = null;
//        try {
////			HttpPost httpPost = new HttpPost("http://localhost:10001/wappay/wappay/pay");
//            HttpPost httpPost = new HttpPost("http://wappay.vitongpay.com/wappay/pay");
//            System.out.println(XmlUtils.parseXML(map));
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            params.add(new BasicNameValuePair("xml", XmlUtils.parseXML(map)));
//            httpPost.setEntity(new UrlEncodedFormEntity(params));
////			StringEntity entityParams = new StringEntity(
////					XmlUtils.parseXML(map), "utf-8");
////			httpPost.setEntity(entityParams);
//            client = HttpClients.createDefault();
//            response = client.execute(httpPost);
//            if(response != null && response.getEntity() != null){
//                String result = EntityUtils.toString(response.getEntity());
//                System.out.println("result="+result);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

}
