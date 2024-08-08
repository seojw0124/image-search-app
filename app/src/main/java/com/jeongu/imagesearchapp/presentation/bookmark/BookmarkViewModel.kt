package com.jeongu.imagesearchapp.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeongu.imagesearchapp.data.repository.BookMarkRepository
import com.jeongu.imagesearchapp.presentation.SearchResultInfo

class BookmarkViewModel(private val repository: BookMarkRepository) : ViewModel() {
    private val _bookmarkList: MutableLiveData<List<SearchResultInfo>> = MutableLiveData()
    val bookmarkList: LiveData<List<SearchResultInfo>> get() = _bookmarkList

    init {
        loadBookMarks()
    }

    private fun loadBookMarks() {
        _bookmarkList.value = repository.bookmarkItems
    }

    fun addBookmarkItem(item: SearchResultInfo) {
        repository.addBookmarkItem(item)
        loadBookMarks()
    }

    fun removeBookmarkItem(item: SearchResultInfo) {
        repository.removeBookmarkItem(item)
        loadBookMarks()
    }
}