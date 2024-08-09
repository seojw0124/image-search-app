package com.jeongu.imagesearchapp.di

import com.jeongu.imagesearchapp.data.remote.KakaoApi
import com.jeongu.imagesearchapp.data.repository.SearchResultRepositoryImpl
import com.jeongu.imagesearchapp.domain.SearchResultRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object SearchResultRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideSearchResultRepository(
        kakaoApi: KakaoApi
    ): SearchResultRepository = SearchResultRepositoryImpl(kakaoApi)
}