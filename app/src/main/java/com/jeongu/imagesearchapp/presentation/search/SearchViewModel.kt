package com.jeongu.imagesearchapp.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongu.imagesearchapp.data.repository.SearchResultRepositoryImpl
import com.jeongu.imagesearchapp.domain.SearchResultRepository
import com.jeongu.imagesearchapp.presentation.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.sortedByDatetime
import com.jeongu.imagesearchapp.presentation.toImageInfo
import com.jeongu.imagesearchapp.presentation.toVideoInfo
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "SearchViewModel"

class SearchViewModel(
    private val repository: SearchResultRepositoryImpl
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<SearchResultInfo>>()
    val searchResult: LiveData<List<SearchResultInfo>> = _searchResult

    fun fetchSearchResult(query: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
                val imageResult = repository.searchImages(query, page).documents?.toImageInfo() ?: emptyList()
                val videoResult = repository.searchVideos(query, page).documents?.toVideoInfo() ?: emptyList()

                _searchResult.value = (imageResult + videoResult).sortedByDatetime()
            }.onFailure {
                Log.e(TAG, "fetchSearchResult() onFailure: ${it.message}")
                handleException(it)
            }
        }
    }

    private fun handleException(e: Throwable) {
        when (e) {
            is HttpException -> {
                val errorJsonString = e.response()?.errorBody()?.string()
                Log.e(TAG, "HTTP error: $errorJsonString")
            }

            is IOException -> Log.e(TAG, "Network error: $e")
            else -> Log.e(TAG, "Unexpected error: $e")
        }
    }
}