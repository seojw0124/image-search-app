package com.jeongu.imagesearchapp.domain

import com.jeongu.imagesearchapp.data.model.ImageResponse
import com.jeongu.imagesearchapp.data.model.VideoResponse

interface SearchResultRepository {
    suspend fun searchImage(query: String, sort: String, page: Int, size: Int): ImageResponse
    suspend fun searchVideo(query: String, sort: String, page: Int, size: Int): VideoResponse
}