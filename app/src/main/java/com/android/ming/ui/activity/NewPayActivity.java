package com.android.ming.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.ming.R;

/**
 * Created by YO on 2016/9/21.
 */
public class NewPayActivity extends AppCompatActivity {
    TextView okBtn;
    public static void createInstance(Context context) {
        Intent intent = new Intent(context, NewPayActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpay);
        okBtn= (TextView) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayActivity.createInstance(NewPayActivity.this, 1);
                NewPayActivity.this.finish();
            }
        });
    }


}
