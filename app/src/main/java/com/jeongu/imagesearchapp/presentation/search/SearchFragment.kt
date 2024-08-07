package com.jeongu.imagesearchapp.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jeongu.imagesearchapp.databinding.FragmentSearchBinding
import com.jeongu.imagesearchapp.presentation.ImageInfo
import com.jeongu.imagesearchapp.presentation.common.ImageListAdapter

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchListAdapter by lazy {
        ImageListAdapter { item ->
            Toast.makeText(requireContext(), item.siteName, Toast.LENGTH_SHORT).show()
        }
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
        val list = mutableListOf<ImageInfo>()
        for (i in 0 until 10) {
            list.add(
                ImageInfo(
                    id = "$i",
                    thumbnailUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRF1IwK6-SxM83UpFVY6WtUZxXx-phss_gAUfdKbkTfau6VWVkt",
                    siteName = "site $i",
                    dateTime = "date $i"
                )
            )
        }
        searchListAdapter.submitList(list.toList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}