package com.jeongu.imagesearchapp.presentation

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkFragment
import com.jeongu.imagesearchapp.presentation.search.SearchFragment

class HomePagerStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2
    override fun createFragment(position: Int) = when (position) {
        0 -> SearchFragment()
        1 -> BookmarkFragment()
        else -> throw IllegalStateException("Invalid position: $position")
    }
}