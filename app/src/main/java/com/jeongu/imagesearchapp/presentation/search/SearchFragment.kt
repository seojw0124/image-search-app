package com.jeongu.imagesearchapp.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.jeongu.imagesearchapp.databinding.FragmentSearchBinding
import com.jeongu.imagesearchapp.presentation.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkViewModel
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkViewModelFactory
import com.jeongu.imagesearchapp.presentation.common.SearchResultAdapter
import com.jeongu.imagesearchapp.presentation.containsById
import com.jeongu.imagesearchapp.presentation.copy
import com.jeongu.imagesearchapp.presentation.id
import com.jeongu.imagesearchapp.presentation.isBookmarked

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
    }
    private val bookmarkViewModel by activityViewModels<BookmarkViewModel> {
        BookmarkViewModelFactory(requireContext())
    }
    private val searchListAdapter by lazy {
        SearchResultAdapter { item ->
//            Toast.makeText(requireContext(), item.isBookmarked.toString(), Toast.LENGTH_SHORT).show()
            if (item.isBookmarked) {
                //val bookmarkItem = item.copy(isBookmarked = false)
                bookmarkViewModel.removeBookmarkItem(item)
            } else {
                val bookmarkItem = item.copy(isBookmarked = true)
                bookmarkViewModel.addBookmarkItem(bookmarkItem)
            }
        }
    }
    private lateinit var searchResultList: MutableList<SearchResultInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO 나중에 저장된 검색어 가져오면 초기화해야함.
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
    }

    private fun initView() = with(binding) {
        rvSearchResultList.adapter = searchListAdapter
        if (etInputSearch.text.toString().isNotBlank()) {
            setSearchResult()
        }
        btnSearch.setOnClickListener {
            setSearchResult()
            hideKeyboard()
        }
    }
    
    private fun setSearchResult() {
        searchViewModel.fetchSearchResult(binding.etInputSearch.text.toString())
        initViewModel()
    }

    private fun initViewModel() = with(searchViewModel) {
        searchResult.observe(viewLifecycleOwner) {
            searchResultList.addAll(it)
            searchListAdapter.submitList(searchResultList.toList())
        }
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