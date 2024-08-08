package com.jeongu.imagesearchapp.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jeongu.imagesearchapp.domain.BookmarkRepository
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.containsById
import com.jeongu.imagesearchapp.presentation.id
import com.jeongu.imagesearchapp.util.Constants.PREF_BOOKMARKS
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(@ApplicationContext context: Context) : BookmarkRepository {
    private val gson = GsonBuilder().registerTypeAdapter(SearchResultInfo::class.java, CustomDeserializer()).create()
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_BOOKMARKS, Context.MODE_PRIVATE)
    private val _bookmarkItems = loadBookmarks()

    override val bookmarkItems: List<SearchResultInfo>
        get() = _bookmarkItems.toList()

    override fun addBookmarkItem(item: SearchResultInfo) {
        if (!_bookmarkItems.containsById(item.id)) {
            _bookmarkItems.add(0, item)
            saveBookmarks()
        }
    }

    override fun removeBookmarkItem(item: SearchResultInfo) {
        _bookmarkItems.remove(item)
        saveBookmarks()
    }

    override fun saveBookmarks() {
        val json = gson.toJson(_bookmarkItems)
        sharedPreferences.edit().putString(PREF_BOOKMARKS, json).apply()
    }

    override fun loadBookmarks(): MutableList<SearchResultInfo> {
        val json = sharedPreferences.getString(PREF_BOOKMARKS, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<SearchResultInfo>>() {}.type
        return gson.fromJson(json, type)
    }
}