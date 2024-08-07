package com.jeongu.imagesearchapp.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jeongu.imagesearchapp.databinding.FragmentBookmarkBinding
import com.jeongu.imagesearchapp.presentation.ImageInfo
import com.jeongu.imagesearchapp.presentation.common.ImageListAdapter

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val bookmarkListAdapter by lazy {
        ImageListAdapter { item ->
            Toast.makeText(requireContext(), item.siteName, Toast.LENGTH_SHORT).show()
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
    }

    private fun initView() = with(binding) {
        rvSearchResultList.adapter = bookmarkListAdapter
        val list = mutableListOf<ImageInfo>()
        for (i in 0 until 10) {
            list.add(
                ImageInfo(
                    id = "$i",
                    thumbnailUrl = "https://health.chosun.com/site/data/img_dir/2023/07/17/2023071701753_0.jpg",
                    siteName = "site $i",
                    dateTime = "date $i"
                )
            )
        }
        bookmarkListAdapter.submitList(list.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}