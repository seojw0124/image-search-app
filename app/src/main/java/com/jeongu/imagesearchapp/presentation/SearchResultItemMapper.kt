package com.jeongu.imagesearchapp.presentation

import com.jeongu.imagesearchapp.data.model.ImageDocument
import com.jeongu.imagesearchapp.data.model.VideoDocument
import com.jeongu.imagesearchapp.util.Constants.SEARCH_TYPE_IMAGE
import com.jeongu.imagesearchapp.util.Constants.SEARCH_TYPE_VIDEO

fun List<ImageDocument>.toImageInfo(): List<SearchResultInfo> {
    return this.map {
        SearchResultInfo.ImageInfo(
            id = it.docUrl ?: "",
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

// val  String.removeComma get() = replace(",", "")
val SearchResultInfo.isBookmarked: Boolean
    get() = when (this) {
        is SearchResultInfo.ImageInfo -> isBookmarked
        is SearchResultInfo.VideoInfo -> isBookmarked
    }

fun List<SearchResultInfo>.sortedByDescendingDatetime(): List<SearchResultInfo> {
    return sortedByDescending {
        when (it) {
            is SearchResultInfo.ImageInfo -> it.dateTime
            is SearchResultInfo.VideoInfo -> it.dateTime
        }
    }
}

fun SearchResultInfo.copy(isBookmarked: Boolean): SearchResultInfo {
    return when (this) {
        is SearchResultInfo.ImageInfo -> this.copy(isBookmarked = isBookmarked)
        is SearchResultInfo.VideoInfo -> this.copy(isBookmarked = isBookmarked)
    }
}

val SearchResultInfo.id: String
    get() {
        return when (this) {
            is SearchResultInfo.ImageInfo -> id
            is SearchResultInfo.VideoInfo -> id
        }
    }

fun MutableList<SearchResultInfo>.containsById(id: String): Boolean {
    return any {
        when (it) {
            is SearchResultInfo.ImageInfo -> it.id == id
            is SearchResultInfo.VideoInfo -> it.id == id
        }
    }
}