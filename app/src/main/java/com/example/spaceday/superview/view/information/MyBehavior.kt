package com.example.spaceday.superview.view.information

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class MyBehavior  @JvmOverloads constructor(
    context : Context? = null,
    attr : AttributeSet? = null
): CoordinatorLayout.Behavior<View>() {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ) = dependency is AppBarLayout

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        val appBarLayout = dependency as AppBarLayout
        val currentAppBarHeight = appBarLayout.height + appBarLayout.y
        val parentHeight = parent.height
        val placeHolderHeight = (parentHeight - currentAppBarHeight).toInt()
        child.layoutParams?.height = placeHolderHeight

        val backgroundColor = (255 * (currentAppBarHeight / appBarLayout.height)).toInt()
        val drawableBackgroundColor = ColorDrawable(Color.rgb(backgroundColor, backgroundColor, 255))
        child.background = drawableBackgroundColor

        child.requestLayout()
        return false
    }
}