package com.jeongu.imagesearchapp.data.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("meta")
    val meta: Meta?,
    @SerializedName("documents")
    val documents: List<VideoDocument>?
)
