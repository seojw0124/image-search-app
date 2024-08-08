package com.jeongu.imagesearchapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jeongu.imagesearchapp.presentation.SearchResultInfo
import com.jeongu.imagesearchapp.util.Constants.BOOKMARKS

class BookMarkRepository(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(BOOKMARKS, Context.MODE_PRIVATE)
    private val gson = Gson()
    private val _bookmarkItems = mutableListOf<SearchResultInfo>()
    val bookmarkItems: List<SearchResultInfo>
        get() = _bookmarkItems.toList()

    fun addBookmarkItem(item: SearchResultInfo) {
        if (!_bookmarkItems.contains(item)) {
            _bookmarkItems.add(0, item)
            saveBookmarks()
        }
    }

    fun removeBookmarkItem(item: SearchResultInfo) {
        _bookmarkItems.remove(item)
        saveBookmarks()
    }

    private fun saveBookmarks() {
        val json = gson.toJson(_bookmarkItems)
        sharedPreferences.edit().putString(BOOKMARKS, json).apply()
    }

    private fun loadBookmarks(): MutableList<SearchResultInfo> {
        val json = sharedPreferences.getString(BOOKMARKS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<SearchResultInfo>>() {}.type
        return gson.fromJson(json, type)
    }
}