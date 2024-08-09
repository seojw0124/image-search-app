package com.jeongu.imagesearchapp.di

import android.content.Context
import com.jeongu.imagesearchapp.data.repository.BookmarkRepositoryImpl
import com.jeongu.imagesearchapp.domain.BookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object BookmarkRepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideBookmarkRepository(
        @ApplicationContext context: Context
    ): BookmarkRepository = BookmarkRepositoryImpl(context)
}