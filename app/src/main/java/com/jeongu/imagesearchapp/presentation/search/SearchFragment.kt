package com.jeongu.imagesearchapp.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.jeongu.imagesearchapp.R
import com.jeongu.imagesearchapp.databinding.FragmentSearchBinding
import com.jeongu.imagesearchapp.presentation.model.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkViewModel
import com.jeongu.imagesearchapp.presentation.common.SearchResultAdapter
import com.jeongu.imagesearchapp.presentation.containsById
import com.jeongu.imagesearchapp.presentation.copy
import com.jeongu.imagesearchapp.presentation.id
import com.jeongu.imagesearchapp.presentation.isBookmarked
import com.jeongu.imagesearchapp.util.Constants.PREF_SEARCH_HISTORY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel>()
    private val bookmarkViewModel by activityViewModels<BookmarkViewModel>()
    private val searchListAdapter by lazy {
        SearchResultAdapter { item ->
            if (item.isBookmarked) {
                bookmarkViewModel.removeBookmarkItem(item)
            } else {
                val bookmarkItem = item.copy(isBookmarked = true)
                bookmarkViewModel.addBookmarkItem(bookmarkItem)
            }
        }
    }
    private lateinit var searchResultList: MutableList<SearchResultInfo>
    private lateinit var searchHistoryList: MutableSet<String>
    private lateinit var searchText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE)
        searchHistoryList = sharedPref.getStringSet(PREF_SEARCH_HISTORY, mutableSetOf()) ?: mutableSetOf()
        searchText = searchHistoryList.lastOrNull() ?: ""

        searchResultList = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initScrollToTopButton()
    }

    private fun initView() = with(binding) {
        rvSearchResultList.adapter = searchListAdapter
        if (searchText.isNotBlank()) {
            etInputSearch.setText(searchText)
            setSearchResult()
        }
        btnSearch.setOnClickListener {
            saveSearchHistory()
            setSearchResult()
            hideKeyboard()
        }
    }

    private fun saveSearchHistory() {
        val searchHistoryText = binding.etInputSearch.text.toString()
        searchHistoryList.add(searchHistoryText)
        val sharedPref = requireActivity().getSharedPreferences(PREF_SEARCH_HISTORY, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putStringSet(PREF_SEARCH_HISTORY, searchHistoryList)
        editor.apply()
    }
    
    private fun setSearchResult() {
        searchViewModel.fetchSearchResult(binding.etInputSearch.text.toString())
        initViewModel()
    }

    private fun initViewModel() = with(searchViewModel) {
        searchResult.observe(viewLifecycleOwner) {
            searchResultList.clear()
            searchResultList.addAll(it)
            bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) { bookmarks ->
                searchResultList.forEachIndexed { index, searchResultInfo ->
                    if (bookmarks.toMutableList().containsById(searchResultInfo.id)) {
                        searchResultList[index] = searchResultInfo.copy(isBookmarked = true)
                    } else {
                        searchResultList[index] = searchResultInfo.copy(isBookmarked = false)
                    }
                }
                searchListAdapter.submitList(searchResultList.toList())
            }
        }
    }

    private fun initScrollToTopButton() {
        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in_scroll_button)
        val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out_scroll_button)
        var isTop = true

        with(binding) {
            rvSearchResultList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!rvSearchResultList.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        ivScrollToTop.apply {
                            visibility = View.GONE
                            startAnimation(fadeOut)
                        }
                        isTop = true
                    } else {
                        if (isTop) {
                            ivScrollToTop.apply {
                                visibility = View.VISIBLE
                                ivScrollToTop.startAnimation(fadeIn)
                            }
                            isTop = false
                        }
                    }
                }
            })

            ivScrollToTop.setOnClickListener {
                binding.rvSearchResultList.smoothScrollToPosition(0)
            }
        }
    }

    private fun hideKeyboard() {
        if (activity != null && requireActivity().currentFocus != null) {
            val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}