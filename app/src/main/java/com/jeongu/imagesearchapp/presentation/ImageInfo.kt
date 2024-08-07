package com.jeongu.imagesearchapp.presentation

data class ImageInfo(
    val id: String,
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String,
    val isBookMarked: Boolean = false
)
