package com.example.spaceday.superview.view.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView

class CustomVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr : Int = 0,
    defStyleRes : Int = 0
) : VideoView(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}