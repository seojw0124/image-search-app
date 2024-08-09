package com.jeongu.imagesearchapp.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongu.imagesearchapp.domain.SearchResultRepository
import com.jeongu.imagesearchapp.presentation.extensions.sortedByDescendingDatetime
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchResultRepository
) : ViewModel() {
    private val _searchResult = MutableLiveData<List<SearchResultInfo>>()
    val searchResult: LiveData<List<SearchResultInfo>> = _searchResult

    fun fetchSearchResult(query: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
                val imageResult = repository.searchImages(query, page).documents?.toImageInfo() ?: emptyList()
                val videoResult = repository.searchVideos(query, page).documents?.toVideoInfo() ?: emptyList()

                _searchResult.value = (imageResult + videoResult).sortedByDescendingDatetime()
            }.onFailure {
                Log.e(TAG, "fetchSearchResult() onFailure: ${it.message}")
            }
        }
    }
}