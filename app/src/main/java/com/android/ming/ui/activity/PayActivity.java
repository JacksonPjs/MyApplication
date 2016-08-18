package com.android.ming.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
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
import com.android.ming.utils.AppUtil;
import com.android.ming.utils.SPUtil;
import com.android.ming.utils.ToastUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.switfpass.pay.MainApplication;
import com.switfpass.pay.activity.PayPlugin;
import com.switfpass.pay.bean.RequestMsg;
import com.switfpass.pay.service.GetPrepayIdResult;
import com.switfpass.pay.utils.MD5;
import com.switfpass.pay.utils.SignUtils;
import com.switfpass.pay.utils.Util;
import com.switfpass.pay.utils.XmlUtils;
import com.wo.main.WP_App;
import com.wo.main.WP_SDK;
import com.wo.plugin.WP_Event;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class PayActivity extends AppCompatActivity implements View.OnClickListener {
        private TextView okBtn,costTv;
        private String name;
        private int money=48;
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
        private ProgressDialog myDialog;

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
                logo=(ImageView)findViewById(R.id.logo);
                styleOther=(LinearLayout)findViewById(R.id.style);
                styleExit=(RelativeLayout)findViewById(R.id.styleexit);
                vip = 3;
                name="年费会员";
                if (pay == 1) {
//                        vip = 1;
//                        name = "月费会员";
                } else if (pay == 2) {
//                        vip = 2;
//                        name = "永久会员";
                } else {
                        vip = 2;
                        name = "特价永久会员";
                        money=29;
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
                                        money = 38;
                                        vip = 1;
                                        name = "月费会员";

                                }
                        }
                });
                yvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        mvip.setChecked(false);
                                        lvip.setChecked(false);
                                        money = 48;
                                        vip = 3;
                                        name = "年费会员";
                                }
                        }
                });
                lvip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                        yvip.setChecked(false);
                                        mvip.setChecked(false);
                                        money = 58;
                                        vip = 2;
                                        name = "永久会员";
                                }
                        }
                });
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
                                }
                        }
                });
        }

        @Override
        public void onClick(View v) {

                if (v == okBtn) {
                        // 请求计费类型 0:微信;1:银联;2:神州付;3:QQ支付;
                        Log.e("type===", type + "");
                        if (type == WXPAY) {
                                new DoPay(this, MainApplication.PAY_WX_WAP).execute();
                        } else {
                                channel = AppUtil.getMetaData(this, "UMENG_CHANNEL");
                                String pp = AppUtil.getMetaData(this, "CHANNEL_VALUE");
                                Log.e("CHANNEL_VALUE==", pp + "");
                                Log.e("channel=", channel + "money=" + money);
                                orderId = String.valueOf(System.currentTimeMillis());
                                WP_SDK.on_Recharge(PayActivity.this, 10 + "", "会员充值", 100 + "分钱的vip",
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
                                                                                map.put("wft_fee", 1 + "");
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
//            new PaySdk().pay(this, money*100,new IPayCallBack() {
//
//                @Override
//                public void onSucc(PayResult payResult) {
//                    SPUtil.putInt(PayActivity.this, Consts.SP.VIP, vip);
//                    toast("开通成功");
//                    finish();
//                }
//
//                @Override
//                public void onFail(PayResult payResult) {
//                    toast("取消支付");
//                }
//
//                @Override
//                public void onCancel(PayResult payResult) {
//                    toast("取消支付");
//                }
//
//                private void toast(String s){
//                    Toast.makeText(PayActivity.this, "result:"+s, Toast.LENGTH_LONG).show();
//                }
//            });
                        }
                }
        }

        /**
         * 圆形进度条测试..
         */
        public void circle() {
                myDialog = new ProgressDialog(PayActivity.this); // 获取对象
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
        private class DoPay extends AsyncTask<Void, Void, Map<String, String>> {
                private Activity activity;
                private String type;//支付方式
                private Map<String, String> params = new HashMap<>();

                public DoPay(Activity activity, String type) {
                        this.activity = activity;
                        this.type = type;
                        circle();
                }

                @Override
                protected Map<String, String> doInBackground(Void... params) {
                        byte[] buf = Util.httpPost(Consts.WFT.CREATE_ORDER, getEntity());
                        if (buf != null) {
                                String response = new String(buf);
                                GetPrepayIdResult result = new GetPrepayIdResult();
                                result.parseFrom(response);
                                return XmlUtils.parse(response);
                        } else {
                                return null;
                        }
                }

                @Override
                protected void onPostExecute(Map<String, String> result) {
                        myDialog.dismiss();
                        if (result == null) {
                                return;
                        }

                        if (result.get("status").equals("0")) {
                                RequestMsg msg = new RequestMsg();
                                msg.setMoney(Integer.valueOf(params.get("total_fee")));
                                msg.setTokenId(result.get("token_id"));
                                msg.setOutTradeNo(params.get("out_trade_no"));
                                msg.setTradeType(type);
                                PayPlugin.unifiedH5Pay(activity, msg);
                        } else {
                                ToastUtil.show(activity, "创建订单失败," + result.get("message"));
                        }
                }

                private String getEntity() {
                        String channel = AppUtil.getMetaData(activity, Consts.APP.CHANNEL_NAME);
                        params.put("body", name); // 商品名称
                        params.put("service", "unified.trade.pay"); // 支付类型
                        params.put("version", "1.0"); // 版本
                        //params.put("mch_id", Consts.WFT.MCH_ID); // 威富通商户号
                        params.put("mch_id", wxBtn.isChecked() ? Consts.WFT.MCH_ID:Consts.WFT.MCH_ID2); // 威富通商户号
                        params.put("notify_url", Consts.URL.NOTIFY_WFT); // 后台通知url
                        params.put("nonce_str", new Random().nextInt(999999) + ""); // 随机数
                        params.put("out_trade_no", channel + "-" + System.currentTimeMillis()); //订单号
                        params.put("mch_create_ip", "127.0.0.1"); // 机器ip地址
                        params.put("total_fee", String.valueOf(1 * 100)); // 总金额
                        params.put("op_shop_id", channel);
                        // 手Q反扫这个设备号必须要传1ff9fe53f66189a6a3f91796beae39fe
                        params.put("device_info", SPUtil.getString(PayActivity.this, Consts.SP.UID));
                        params.put("limit_credit_pay", "0");// 1限制使用信用卡,0不限制
                        //  params.put("sign", createSign(params, Consts.WFT.SIGN_KEY)); // sign签名
                        params.put("sign", createSign(params, wxBtn.isChecked() ? Consts.WFT.SIGN_KEY:Consts.WFT.SIGN_KEY2)); // sign签名
                        Log.e("signnnnn==",XmlUtils.toXml(params));
                        return XmlUtils.toXml(params);
                }

                private String createSign(Map<String, String> params, String signKey) {
                        StringBuilder buf = new StringBuilder((params.size() + 1) * 10);
                        SignUtils.buildPayParams(buf, params, false);
                        buf.append("&key=").append(signKey);
                        String preStr = buf.toString();
                        String sign;
                        // 获得签名验证结果
                        try {
                                sign = MD5.md5s(preStr).toUpperCase();
                        } catch (Exception e) {
                                sign = MD5.md5s(preStr).toUpperCase();
                        }
                        return sign;
                }
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (data == null) {
                        return;
                }
                String result = data.getExtras().getString("resultCode");
                if ("SUCCESS".equalsIgnoreCase(result)) {
                        // 支付成功
                        SPUtil.putInt(this, Consts.SP.VIP, vip);
                        ToastUtil.show(this, "开通成功");
                        finish();
                } else {
                        ToastUtil.show(this, "取消支付");
                }
        }
}
