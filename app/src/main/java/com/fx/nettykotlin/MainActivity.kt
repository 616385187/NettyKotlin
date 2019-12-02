package com.fx.nettykotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.fx.nettykotlin.lifecycle.J_LifecycleActivity
import com.fx.nettykotlin.lifecycle.K_LifeCycleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        findViewById<Button>(R.id.lifecycle).setOnClickListener { v ->
////            startActivity(Intent(MainActivity@ this, J_LifecycleActivity::class.java))
//            startActivity(Intent(MainActivity@ this, PlayerActivity::class.java))
//        }
//
//        mtv.setText("asdasdasd")

    }


}
