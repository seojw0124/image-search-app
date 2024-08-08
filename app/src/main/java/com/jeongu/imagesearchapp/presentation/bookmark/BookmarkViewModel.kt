package com.jeongu.imagesearchapp.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeongu.imagesearchapp.data.repository.BookMarkRepository
import com.jeongu.imagesearchapp.presentation.SearchResultInfo

class BookmarkViewModel(private val repository: BookMarkRepository) : ViewModel() {
    private val _bookmarks: MutableLiveData<List<SearchResultInfo>> = MutableLiveData()
    val bookmarks: LiveData<List<SearchResultInfo>> get() = _bookmarks

    init {
        loadBookMarks()
    }

    private fun loadBookMarks() {
        _bookmarks.value = repository.bookmarkItems
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