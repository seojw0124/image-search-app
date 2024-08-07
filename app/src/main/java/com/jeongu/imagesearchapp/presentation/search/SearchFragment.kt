package com.jeongu.imagesearchapp.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModelFactory()
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

    private fun initViewModel() = with(searchViewModel) {
        searchResult.observe(viewLifecycleOwner) {
            searchListAdapter.submitList(it)
        }
    }
    
    private fun setSearchResult() {
        searchViewModel.fetchSearchResult(binding.etInputSearch.text.toString())
        initViewModel()
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