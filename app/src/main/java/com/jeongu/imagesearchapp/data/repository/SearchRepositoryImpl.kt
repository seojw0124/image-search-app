package com.jeongu.imagesearchapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.jeongu.imagesearchapp.data.remote.KakaoApi
import com.jeongu.imagesearchapp.data.remote.SearchPagingSource
import com.jeongu.imagesearchapp.domain.SearchRepository
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val kakaoApi: KakaoApi) : SearchRepository {
    override fun loadSearchResult(query: String) = Pager(
        config = PagingConfig(
            pageSize = 15,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SearchPagingSource(kakaoApi, query)
        }
    ).liveData
}