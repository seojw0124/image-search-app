package com.jeongu.imagesearchapp.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.sortedByDescendingDatetime
import com.jeongu.imagesearchapp.presentation.toImageInfo
import com.jeongu.imagesearchapp.presentation.toVideoInfo

class SearchPagingSource(private val kakaoApi: KakaoApi, private val query: String) : PagingSource<Int, SearchResultInfo>() {
    override fun getRefreshKey(state: PagingState<Int, SearchResultInfo>) = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchResultInfo> {
        return try {
            val page = params.key ?: 1
            val imageResult = kakaoApi.searchImages(query = query, page = page).documents?.toImageInfo() ?: emptyList()
            val videoResult = kakaoApi.searchVideos(query = query, page = page).documents?.toVideoInfo() ?: emptyList()
            val result = (imageResult + videoResult).sortedByDescendingDatetime()
            Log.d("SearchPagingSource", "query: $query, page: $page, result: $result")
            LoadResult.Page(
                data = result,
                prevKey = null,
                nextKey = if (result.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}