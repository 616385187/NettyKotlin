package com.fx.nettykotlin.proxy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fx.nettykotlin.R;

import java.lang.reflect.Field;

public class ProxyJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy_java);
        try {
            Class<?> mActivity = Class.forName("android.app.ActivityManager");
            Field field = mActivity.getDeclaredField("gDefault");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}