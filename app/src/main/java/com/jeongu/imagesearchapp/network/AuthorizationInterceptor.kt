package com.jeongu.imagesearchapp.network

import com.jeongu.imagesearchapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
            .build()
        return chain.proceed(newRequest)
    }
}