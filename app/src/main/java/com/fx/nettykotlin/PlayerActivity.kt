package com.fx.nettykotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
//        var params = scrollView.layoutParams as CoordinatorLayout.LayoutParams
//        var behavior = params.behavior as AppBarLayout.ScrollingViewBehavior
//        behavior.overlayTop = 60
    }
}
