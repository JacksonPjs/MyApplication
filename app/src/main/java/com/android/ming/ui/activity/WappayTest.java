package com.android.ming.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.ming.R;
import com.android.ming.app.Consts;
import com.android.ming.utils.HttpUtils;
import com.android.ming.utils.MD5Util;
import com.android.ming.utils.XmlUtils;
import com.android.ming.utils.volley.XMLRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class WappayTest extends BaseActivity {
	TextView activeBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active);
		activeBtn= (TextView) findViewById(R.id.activeBtn);
		activeBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				HttpUtils.sendPost("",)

				RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
				StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://wappay.vitongpay.com/wappay/pay",
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							Log.e("resonResponse",response+"");
						}
					}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("reserror",error.toString()+"");
					}
				})
				{

					@Override
					protected Map<String, String> getParams() {
						//在这里设置需要post的参数
				Map<String, String> map = new HashMap<>();
//				map.put("channel", channel);
//				map.put("wft_orderid", orderId);
//				map.put("wft_fee", money + "");
//				map.put("trade_type", type + "");
						String CustomerNo = "800002100110";
						String OrderNo = "13689637292";
						String OrderAmount = "1";
						String ProductName = "ProductName";
						String ProductDesc = "ProductDesc";
						String PostBackUrl = "http://wappay.vitongpay.com/wappay/callback";
						String NotifyUrl = "http://wappay.vitongpay.com/wappay/callback";
						String ext1 = "Ext1";
						String ext2 = "Ext2";
						String Sign = "Sign";
						String Version="1.0";
						String GoodNo="GN10T";
						String InputCharset="UTF";
						String SignType="MD5";
						String ProductNo="PN10T";
						String key="93117a5dfe3046f787f0dd7fbe0f6807";
						String Origin="Version="+Version+"&InputCharset="+InputCharset+"&CustomerNo="+CustomerNo+"&GoodNo="+GoodNo+"&ProductNo="+ProductNo+"&OrderNo="+OrderNo+"&OrderAmount="
							+OrderAmount +"&ProductName="+ProductName+"&ProductDesc="+ProductDesc+"&PostBackUrl="+PostBackUrl+"&NotifyUrl="+NotifyUrl+"&ext1="+ext1+"&ext2="+ext2;

						map.put("Version","1.0" );
						map.put("InputCharset","UTF-8" );
						map.put("SignType",SignType );
						map.put("CustomerNo", CustomerNo);
						map.put("GoodNo", "GN10T");
						map.put("ProductNo", "PN10T");
						map.put("OrderNo", OrderNo);
						map.put("OrderAmount", OrderAmount);
						map.put("ProductName", ProductName);
						map.put("ProductDesc", ProductDesc);
						map.put("PostBackUrl", PostBackUrl);
						map.put("NotifyUrl", NotifyUrl);
						map.put("ext1", ext1);
						map.put("ext2", ext2);
						map.put("Sign", MD5Util.getMD5(Origin+key));
//						String str=XmlUtils.parseXml(map);
//						map= XmlUtils.transStringToMap(str);
//						Log.e("str==",str);
						Log.e("map",map.toString());
						return map;
					}
				};
				requestQueue.add(stringRequest);
			}
		});
		XMLRequest xmlRequest=new XMLRequest(Request.Method.POST,"http://wappay.vitongpay.com/wappay/pay", new Response.Listener<XmlPullParser>() {
			@Override
			public void onResponse(XmlPullParser xmlPullParser) {

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {

			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return super.getParams();
			}
		};

	}



	public static Intent createIntent(Context context, int id) {
		Intent intent = new Intent(context, WappayTest.class);
		intent.putExtra("IK", id);
		return intent;
	}
}
