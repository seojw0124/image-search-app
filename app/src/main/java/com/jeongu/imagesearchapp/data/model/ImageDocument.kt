package com.jeongu.imagesearchapp.data.model

import com.google.gson.annotations.SerializedName

data class ImageDocument(
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("display_sitename")
    val displaySitename: String?,
    @SerializedName("doc_url")
    val docUrl: String?,
    @SerializedName("datetime")
    val datetime: String?
)
