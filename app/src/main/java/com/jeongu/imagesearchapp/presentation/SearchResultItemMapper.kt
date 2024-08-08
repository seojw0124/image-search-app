package com.jeongu.imagesearchapp.presentation

import com.jeongu.imagesearchapp.data.model.ImageDocument
import com.jeongu.imagesearchapp.data.model.VideoDocument
import java.util.UUID

fun List<ImageDocument>.toImageInfo(): List<SearchResultInfo> {
    return this.map {
        SearchResultInfo.ImageInfo(
            id = UUID.randomUUID().toString(),
            thumbnailUrl = it.thumbnailUrl ?: "",
            siteName = it.displaySitename ?: "",
            docUrl = it.docUrl ?: "",
            dateTime = it.datetime ?: "",
        )
    }
}

fun List<VideoDocument>.toVideoInfo(): List<SearchResultInfo> {
    return this.map {
        SearchResultInfo.VideoInfo(
            id = UUID.randomUUID().toString(),
            thumbnail = it.thumbnail ?: "",
            title = it.title ?: "",
            url = it.url ?: "",
            dateTime = it.datetime ?: "",
        )
    }
}

// val  String.removeComma get() = replace(",", "")
val SearchResultInfo.isBookmarked: Boolean
    get() = when (this) {
        is SearchResultInfo.ImageInfo -> isBookMarked
        is SearchResultInfo.VideoInfo -> isBookMarked
    }

fun List<SearchResultInfo>.sortedByDatetime(): List<SearchResultInfo> {
    return sortedByDescending {
        when (it) {
            is SearchResultInfo.ImageInfo -> it.dateTime
            is SearchResultInfo.VideoInfo -> it.dateTime
        }
    }
}