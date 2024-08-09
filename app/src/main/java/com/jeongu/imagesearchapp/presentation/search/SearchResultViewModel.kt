package com.jeongu.imagesearchapp.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jeongu.imagesearchapp.domain.SearchRepository
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.sortedByDescendingDatetime
import com.jeongu.imagesearchapp.presentation.toImageInfo
import com.jeongu.imagesearchapp.presentation.toVideoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {
    private val _searchResult = MutableStateFlow<PagingData<SearchResultInfo>?>(null)
    val searchResult: StateFlow<PagingData<SearchResultInfo>?> = _searchResult

    fun fetchSearchResult(query: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
//                repository.loadSearchResult(query).observeForever {
//                    _searchResult.postValue(it)
//                }

                repository.loadSearchResult(query).cachedIn(viewModelScope).collectLatest {
                    _searchResult.value = it
                }
            }.onFailure {
                Log.e("SearchResultViewModel", "fetchSearchResult() onFailure: ${it.message}")
            }
        }
    }
}