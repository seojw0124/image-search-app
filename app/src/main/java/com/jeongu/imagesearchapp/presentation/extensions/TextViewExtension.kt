package com.jeongu.imagesearchapp.presentation.extensions

import android.widget.TextView
import com.jeongu.imagesearchapp.R

fun TextView.setTitle(viewType: Int, title: String) {
    text = when (viewType) {
        0 -> context.getString(R.string.format_image_result, title)
        1 -> context.getString(R.string.format_video_result, title)
        else -> title
    }
}