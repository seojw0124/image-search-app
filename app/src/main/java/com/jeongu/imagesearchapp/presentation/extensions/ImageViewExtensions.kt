package com.jeongu.imagesearchapp.presentation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jeongu.imagesearchapp.R

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.color.gray_50)
        .error(R.drawable.ic_image_not_supported)
        .into(this)
}