package com.jeongu.imagesearchapp.presentation.extensions

import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo

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
    val isContains = any {
        when (it) {
            is SearchResultInfo.ImageInfo -> it.id == id
            is SearchResultInfo.VideoInfo -> it.id == id
        }
    }
    return isContains
}