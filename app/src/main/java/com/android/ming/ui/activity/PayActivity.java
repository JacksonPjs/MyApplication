package com.android.ming.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.bean.Pay;
import com.android.ming.bean.PayResult;
import com.android.ming.bean.Video;
import com.android.ming.ui.view.MyDialog;
import com.android.ming.utils.AppUtil;
import com.android.ming.utils.HtmlUtils;
import com.android.ming.utils.SPUtil;
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
import com.switfpass.pay.MainApplication;
import com.wo.main.WP_SDK;
import com.wo.plugin.WP_Event;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class PayActivity extends AppCompatActivity implements View.OnClickListener {
        private TextView okBtn,costTv,vipForever,vipMonth,vipYear,vipTips;
        private String name;
        private int money=50;
        private RadioButton wxBtn, qqBtn, aliBtn;
        private RadioButton mvip, yvip, lvip;
        private int vip;// 会员类型
        int type;
        String channel;
        String orderId;
        int WXPAY = 0;//微信支付
        int QQPAY = 3;//qq支付
        int ALIPAY = 2;//支付宝支付
        RelativeLayout styleExit;
        LinearLayout styleOther;
        ImageView logo;
        private static ProgressDialog myDialog;
        String traceno=null;
        String refno=null;

        public static void createInstance(Context context, int pay) {
                Intent intent = new Intent(context, PayActivity.class);
                intent.putExtra("pay", pay);
                context.startActivity(intent);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_pay);
                setFinishOnTouchOutside(false);// 点击外部不关闭
                int pay = getIntent().getIntExtra("pay", 1);
                mvip = (RadioButton) findViewById(R.id.mvip);
                yvip = (RadioButton) findViewById(R.id.yvip);
                lvip = (RadioButton) findViewById(R.id.lvip);
                costTv=(TextView)findViewById(R.id.costtv);
                vipForever=(TextView)findViewById(R.id.vip_forever);
                vipMonth=(TextView)findViewById(R.id.vip_month);
                vipYear=(TextView)findViewById(R.id.vip_year);
                vipTips=(TextView)findViewById(R.id.vip_tips);
                logo=(ImageView)findViewById(R.id.logo);
                styleOther=(LinearLayout)findViewById(R.id.style);
                styleExit=(RelativeLayout)findViewById(R.id.styleexit);
                vip = 3;
                name="年费会员";
                type=WXPAY;
                if (pay == 1) {
//                        vip = 1;
//                        name = "月费会员";
                } else if (pay == 2) {
//                        vip = 2;
//                        name = "永久会员";
                } else {
                        vip = 4;
                        name = "特价永久会员";
                        logo.setBackgroundResource(R.mipmap.exit_logo);
                        costTv.getPaint().setAntiAlias(true);//抗锯齿
                        costTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
                        styleExit.setVisibility(View.VISIBLE);
                        styleOther.setVisibility(View.GONE);
                }
                mvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        yvip.setChecked(false);
                                        lvip.setChecked(false);
                                        vip = 1;
                                        name = "月费会员";
//                                        if (type!=WXPAY){
                                                money=40;
//                                        }else {
//                                                money=38;
//                                        }

                                }
                        }
                });
                yvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        mvip.setChecked(false);
                                        lvip.setChecked(false);
                                        vip = 3;
                                        name = "年费会员";
//                                        if (type!=WXPAY){
                                                money=50;
//                                        }else {
//                                                money=48;
//                                        }
                                }
                        }
                });
                lvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        yvip.setChecked(false);
                                        mvip.setChecked(false);
                                        vip = 2;
                                        name = "永久会员";
//                                        if (type!=WXPAY){
                                                money=100;
//                                        }else {
//                                                money=58;
//                                        }
                                }
                        }
                });
//                switch (vip){
//                        case  1:
//                                money=38;
//                                break;
//                        case  3:
//                                money=48;
//                                break;
//                        case  2:
//                                money=58;
//                                break;
//                        case  4:
//                                money=29;
//                                break;
//                }
                okBtn = (TextView) findViewById(R.id.okBtn);
                okBtn.setOnClickListener(this);
                wxBtn = (RadioButton) findViewById(R.id.wxBtn);
                qqBtn = (RadioButton) findViewById(R.id.qqBtn);
                aliBtn = (RadioButton) findViewById(R.id.aliBtn);
                wxBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        qqBtn.setChecked(false);
                                        aliBtn.setChecked(false);
                                        type = WXPAY;
//                                        vipTips.setText("29元");
//                                        vipMonth.setText("38元");
//                                        vipYear.setText("48元");
//                                        vipForever.setText("58元");
//                                        switch (vip){
//                                                case  1:
//                                                        money=38;
//                                                        break;
//                                                case  3:
//                                                        money=48;
//                                                        break;
//                                                case  2:
//                                                        money=58;
//                                                        break;
//                                                case  4:
//                                                        money=29;
//                                                        break;
//                                        }
                                }
                        }
                });
                qqBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        wxBtn.setChecked(false);
                                        aliBtn.setChecked(false);
                                        type = QQPAY;
//                                        vipTips.setText("30元");
//                                        vipMonth.setText("40元");
//                                        vipYear.setText("50元");
//                                        vipForever.setText("100元");
//                                        switch (vip){
//                                                case  1:
//                                                        money=40;
//                                                        break;
//                                                case  3:
//                                                        money=50;
//                                                        break;
//                                                case  2:
//                                                        money=100;
//                                                        break;
//                                                case  4:
//                                                        money=30;
//                                                        break;
//                                        }

                                }
                        }
                });
                aliBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        qqBtn.setChecked(false);
                                        wxBtn.setChecked(false);
                                        type = ALIPAY;
//                                        vipTips.setText("30元");
//                                        vipMonth.setText("40元");
//                                        vipYear.setText("50元");
//                                        vipForever.setText("100元");
//                                        switch (vip){
//                                                case  1:
//                                                        money=40;
//                                                        break;
//                                                case  3:
//                                                        money=50;
//                                                        break;
//                                                case  2:
//                                                        money=100;
//                                                        break;
//                                                case  4:
//                                                        money=30;
//                                                        break;
//                                        }
                                }
                        }
                });
        }

        @Override
        public void onClick(View v) {

                if (v == okBtn) {



//                        // 请求计费类型 0:微信;1:银联;2:神州付;3:QQ支付;
//                        if (type == WXPAY) {
//                                MyDialog.circle(PayActivity.this);
//
//                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST,Consts.WapPay.WAPPAY,
//                                        new Response.Listener<String>() {
//                                                @Override
//                                                public void onResponse(String response) {
//                                                        Gson gson=new Gson();
////
//                                                        final Pay pay=gson.fromJson(response,new TypeToken<Pay>(){}.getType());
//                                                        if (pay.getRespCode().equals("00")) {
//                                                               String url=pay.getBarCode();
//                                                                traceno=pay.getTraceno();
//                                                                refno=pay.getRefno();
//                                                                new DoPay(PayActivity.this, url).execute();
////                                                                new Thread(){
////                                                                        @Override
////                                                                        public void run() {
////                                                                                super.run();
////                                                                                String html = HtmlUtils.getHtmlString(url);
////                                                                                Document document= Jsoup.parse(html);
////                                                                                Element element= document.getElementById("cli");
////                                                                                String s=element.attr("href");
////                                                                                String ur=   URLDecoder.decode(s);
////                                                                                Intent intent = new Intent();
////                                                                                intent.setAction("android.intent.action.VIEW");
////                                                                                Uri content_url = Uri.parse(ur);
////                                                                                intent.setData(content_url);
////                                                                                startActivityForResult(intent,108);
////                                                                        }
////                                                                }.start();
////
//                                                        }
//                                                }
//                                        }, new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                                Log.e("res",error.toString());
//                                        }
//                                }) {
//                                        @Override
//                                        protected Map<String, String> getParams() {
//                                                //在这里设置需要post的参数
//                                                Map<String, String> param = new HashMap<>();
////                                                int payType=2;
////                                                if (type==ALIPAY){
////                                                        payType=1;
////                                                }
//                                                try {
//                                                param.put("merchno", Consts.WapPay.MCH_ID);
//                                                param.put("amount", ""+0.01);
//                                                param.put("payType", "2");//1-支付宝 2-微信
//                                                param.put("traceno", WapPayUtils.generateTraceno());
//                                                        param.put("notifyUrl", Consts.WapPay.NOTIFY_URL);
//
//
//                                                        param.put("signature", WapPayUtils.signature(param, Consts.WapPay.SIGN_KEY));
//                                                } catch (Exception e) {
//                                                        e.printStackTrace();
//                                                }
//                                                return param;
//                                        }
//                                };
//
//                                requestQueue.add(stringRequest);
//
//                        } else {
                                channel = AppUtil.getMetaData(this, "UMENG_CHANNEL");
                                String pp = AppUtil.getMetaData(this, "CHANNEL_VALUE");
                                Log.e("CHANNEL_VALUE==", pp + "");
                                Log.e("channel=", channel + "money=" + money);
                                orderId = String.valueOf(System.currentTimeMillis());
                                WP_SDK.on_Recharge(PayActivity.this, money*100+ "", "会员充值", money*100 + "分钱的vip",
                                        orderId, type, new WP_Event() {
                                                @Override
                                                public void on_Result(int code, String value) {
                                                        // TODO Auto-generated method stub
                                                        Log.e("debug", code + ",value=" + value + ",type==" + type);
                                                        if (code == 0) {
                                                                // 充值成功
                                                                SPUtil.putInt(PayActivity.this, Consts.SP.VIP, vip);
                                                                Toast.makeText(PayActivity.this, "充值成功!", Toast.LENGTH_LONG).show();
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
                                                                                map.put("channel", channel);
                                                                                map.put("wft_orderid", orderId);
                                                                                map.put("wft_fee", money + "");
                                                                                map.put("trade_type", type + "");
                                                                                return map;
                                                                        }
                                                                };

                                                                requestQueue.add(stringRequest);
                                                                finish();
                                                        } else {// 充值失败
                                                                Toast.makeText(PayActivity.this, "充值失败!", Toast.LENGTH_LONG).show();
                                                        }
                                                }
                                        });
//                        }
                }
        }

        public class  DoPay extends  AsyncTask<Void,Void,Void>{
                private Activity activity;
                private String url;//支付方式
                public DoPay(Activity activity, String url) {
                        this.activity=activity;
                        this.url=url;

                }

                @Override
                protected Void doInBackground(Void... params) {
                        String html = HtmlUtils.getHtmlString(url);
                        Document document= Jsoup.parse(html);
                        Element element= document.getElementById("cli");
                        String s=element.attr("href");
                        String ur=   URLDecoder.decode(s);
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(ur);
                        intent.setData(content_url);
                        startActivityForResult(intent,108);
                        return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        MyDialog.hide();
                }
        }



        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//                if (data == null) {
//                        return;
//                }
                if (requestCode==108){
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

                                                        SPUtil.putInt(PayActivity.this, Consts.SP.VIP, vip);
                                                        ToastUtil.show(PayActivity.this, "开通成功");
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
                                                                        String channel = AppUtil.getMetaData(PayActivity.this, "UMENG_CHANNEL");
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
                                                        ToastUtil.show(PayActivity.this, "取消支付");
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
