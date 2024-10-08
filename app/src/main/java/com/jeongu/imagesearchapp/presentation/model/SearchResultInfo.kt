package com.jeongu.imagesearchapp.presentation.model

sealed class SearchResultInfo {
    data class ImageInfo(
        val id: String,
        val type: String,
        val thumbnailUrl: String,
        val siteName: String,
        val docUrl: String,
        val dateTime: String,
        val isBookmarked: Boolean = false
    ) : SearchResultInfo()

    data class VideoInfo(
        val id: String,
        val type: String,
        val thumbnail: String,
        val title: String,
        val url: String,
        val dateTime: String,
        val isBookmarked: Boolean = false
    ) : SearchResultInfo()
}