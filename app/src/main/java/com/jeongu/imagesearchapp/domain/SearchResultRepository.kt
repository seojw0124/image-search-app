package com.jeongu.imagesearchapp.domain

import com.jeongu.imagesearchapp.data.model.ImageResponse
import com.jeongu.imagesearchapp.data.model.VideoResponse

interface SearchResultRepository {
    suspend fun searchImages(query: String, page: Int): ImageResponse
    suspend fun searchVideos(query: String, page: Int): VideoResponse
}