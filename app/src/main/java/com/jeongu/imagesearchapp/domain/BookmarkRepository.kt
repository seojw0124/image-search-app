package com.jeongu.imagesearchapp.domain

import com.jeongu.imagesearchapp.presentation.SearchResultInfo

interface BookmarkRepository {

    val bookmarkItems: List<SearchResultInfo>

    fun addBookmarkItem(item: SearchResultInfo)

    fun removeBookmarkItem(item: SearchResultInfo)

    fun saveBookmarks()

    fun loadBookmarks(): MutableList<SearchResultInfo>
}