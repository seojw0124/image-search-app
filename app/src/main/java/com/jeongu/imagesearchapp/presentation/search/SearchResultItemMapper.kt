package com.jeongu.imagesearchapp.presentation.search

import com.jeongu.imagesearchapp.data.model.ImageDocument
import com.jeongu.imagesearchapp.data.model.VideoDocument
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.util.Constants.SEARCH_TYPE_IMAGE
import com.jeongu.imagesearchapp.util.Constants.SEARCH_TYPE_VIDEO

fun List<ImageDocument>.toImageInfo(): List<SearchResultInfo> {
    return this.map {
        SearchResultInfo.ImageInfo(
            id = it.imageUrl ?: "",
            type = SEARCH_TYPE_IMAGE,
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
            id = it.url ?: "",
            type = SEARCH_TYPE_VIDEO,
            thumbnail = it.thumbnail ?: "",
            title = it.title ?: "",
            url = it.url ?: "",
            dateTime = it.datetime ?: "",
        )
    }
}