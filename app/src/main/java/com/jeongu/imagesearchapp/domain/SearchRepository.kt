package com.jeongu.imagesearchapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun loadSearchResult(query: String): Flow<PagingData<SearchResultInfo>>
}