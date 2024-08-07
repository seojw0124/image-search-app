package com.jeongu.imagesearchapp.presentation

import com.jeongu.imagesearchapp.data.model.ImageDocument
import java.util.UUID

fun List<ImageDocument>.toImageInfo(): List<ImageInfo> {
    return this.map {
        ImageInfo(
            id = UUID.randomUUID().toString(),
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            dateTime = it.datetime ?: "",
        )
    }
}