package com.jeongu.imagesearchapp.data.remote

import com.jeongu.imagesearchapp.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/search/image")
    suspend fun getImageList(
        @Query("query") search: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): ImageResponse
}