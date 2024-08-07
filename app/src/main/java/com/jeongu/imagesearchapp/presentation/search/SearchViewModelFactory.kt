package com.jeongu.imagesearchapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jeongu.imagesearchapp.data.repository.SearchResultRepositoryImpl

class SearchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(SearchResultRepositoryImpl()) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}