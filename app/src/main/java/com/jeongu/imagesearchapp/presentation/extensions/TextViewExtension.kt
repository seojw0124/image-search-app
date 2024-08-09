package com.jeongu.imagesearchapp.presentation.extensions

import android.widget.TextView
import com.jeongu.imagesearchapp.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun TextView.setTitle(viewType: Int, title: String) {
    text = when (viewType) {
        0 -> context.getString(R.string.format_image_result, title)
        1 -> context.getString(R.string.format_video_result, title)
        else -> title
    }
}

fun TextView.setDateTime(dateTime: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val parsedDateTime = OffsetDateTime.parse(dateTime)
    text = parsedDateTime.format(formatter)
}