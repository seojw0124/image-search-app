package com.jeongu.imagesearchapp.data.model

import com.google.gson.annotations.SerializedName

data class VideoDocument(
    @SerializedName("title")
    val title: String?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("author")
    val author: String?
)