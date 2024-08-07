package com.jeongu.imagesearchapp.data.repository

import com.jeongu.imagesearchapp.data.model.ImageResponse
import com.jeongu.imagesearchapp.data.model.VideoResponse
import com.jeongu.imagesearchapp.domain.SearchResultRepository
import com.jeongu.imagesearchapp.network.RetrofitClient

class SearchResultRepositoryImpl : SearchResultRepository {
    override suspend fun searchImages(query: String, page: Int): ImageResponse {
        return RetrofitClient.kakaoAPI.searchImages(query = query, page = page)
    }

    override suspend fun searchVideos(query: String, page: Int): VideoResponse {
        return RetrofitClient.kakaoAPI.searchVideos(query = query, page = page)
    }
}