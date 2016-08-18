package com.android.ming.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.ming.R;

/**
 * Created by YO on 2016/8/13.
 */
public class SearchFailActivity extends Activity {
        TextView title,okBtn;
        Button search_btn_back;
        public static void createInstance(Context context, String title) {
                Intent intent = new Intent(context, SearchFailActivity.class);
                intent.putExtra("title", title);
                context.startActivity(intent);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.search_fail);
                title= (TextView) findViewById(R.id.title);
                okBtn= (TextView) findViewById(R.id.okBtn);
                search_btn_back= (Button) findViewById(R.id.search_btn_back);
                search_btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                finish();
                        }
                });

                okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                PayActivity.createInstance(SearchFailActivity.this, 1);
                                SearchFailActivity.this.finish();
                        }
                });
                String tip=getIntent().getStringExtra("title");
                title.setText(tip);


        }


}
