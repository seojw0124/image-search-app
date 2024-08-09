package com.jeongu.imagesearchapp.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jeongu.imagesearchapp.databinding.FragmentBookmarkBinding
import com.jeongu.imagesearchapp.presentation.common.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel by activityViewModels<BookmarkViewModel>()
    private val bookmarkListAdapter by lazy {
        SearchResultAdapter { item ->
            bookmarkViewModel.removeBookmarkItem(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        rvSearchResultList.adapter = bookmarkListAdapter
    }

    private fun initViewModel() = with(bookmarkViewModel) {
        bookmarks.observe(viewLifecycleOwner) {
            bookmarkListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}