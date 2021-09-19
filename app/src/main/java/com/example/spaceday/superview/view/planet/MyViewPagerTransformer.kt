package com.example.spaceday.superview.view.planet

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2

class MyViewPagerTransformer: ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            Log.i("transform", "$position ${view.id}")
            when {
                position < -1 -> {
                    alpha = 0f
                }
                position <= 0 -> {
                    alpha = 1 + position
                    translationX = pageWidth * - position
                    val scaleFactor = (1 - Math.abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                position <= 1 -> {
                    alpha = 1 - position
                    translationX = pageWidth * - position
                    val scaleFactor = (1 - Math.abs(position))
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                }
                else -> {
                    alpha = 0f
                }
            }
        }
    }
}