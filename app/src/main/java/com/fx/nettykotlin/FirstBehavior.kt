package com.fx.nettykotlin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout

class FirstBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        Log.i(javaClass.simpleName+"_layoutDependsOn","dependency-->"+dependency+"child-->"+child)
        return super.layoutDependsOn(parent, child, dependency)
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {

        Log.i(javaClass.simpleName+"_onDependentViewChanged","dependency-->"+dependency+"child-->"+child)
        return super.onDependentViewChanged(parent, child, dependency)
    }
}