package com.jeongu.imagesearchapp.data.repository

import com.jeongu.imagesearchapp.data.model.ImageResponse
import com.jeongu.imagesearchapp.data.model.VideoResponse
import com.jeongu.imagesearchapp.data.remote.KakaoApi
import com.jeongu.imagesearchapp.domain.SearchResultRepository
import com.jeongu.imagesearchapp.network.RetrofitClient
import javax.inject.Inject

class SearchResultRepositoryImpl @Inject constructor(private val kakaoApi: KakaoApi) : SearchResultRepository {
    override suspend fun searchImages(query: String, page: Int): ImageResponse {
        return kakaoApi.searchImages(query = query, page = page)
    }

    override suspend fun searchVideos(query: String, page: Int): VideoResponse {
        return kakaoApi.searchVideos(query = query, page = page)
    }
}