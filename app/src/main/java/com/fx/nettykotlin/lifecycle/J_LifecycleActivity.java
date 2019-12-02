package com.fx.nettykotlin.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fx.nettykotlin.R;

public class J_LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        getLifecycle().addObserver(new LifeCycle_J());
    }



}
