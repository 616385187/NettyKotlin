package com.fx.nettykotlin.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fx.nettykotlin.R

class K_LifeCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_k__life_cycle)

        lifecycle.addObserver( LifeCycle())
        lifecycle.addObserver(LifeCycle_J())
    }
}
