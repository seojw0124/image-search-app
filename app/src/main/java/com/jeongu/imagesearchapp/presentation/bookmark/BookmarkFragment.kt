package com.jeongu.imagesearchapp.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jeongu.imagesearchapp.databinding.FragmentBookmarkBinding
import com.jeongu.imagesearchapp.presentation.SearchResultInfo
import com.jeongu.imagesearchapp.presentation.common.SearchResultAdapter
import com.jeongu.imagesearchapp.presentation.copy
import com.jeongu.imagesearchapp.presentation.isBookmarked

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel by activityViewModels<BookmarkViewModel> {
        BookmarkViewModelFactory(requireContext())
    }
    private val bookmarkListAdapter by lazy {
        SearchResultAdapter { item ->
            //Toast.makeText(requireContext(), item.isBookmarked.toString(), Toast.LENGTH_SHORT).show()
            val bookmarkItem = item.copy(isBookmarked = !item.isBookmarked)
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
        bookmarkList.observe(viewLifecycleOwner) {
            bookmarkListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}