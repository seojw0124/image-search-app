package com.jeongu.imagesearchapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.jeongu.imagesearchapp.R
import com.jeongu.imagesearchapp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val menuTabTitles = mapOf(
        "검색" to R.drawable.ic_search,
        "북마크" to R.drawable.ic_bookmark
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnApplyWindowInsetsListener()
        initView()
    }

    private fun setOnApplyWindowInsetsListener() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initView() = with(binding) {
        viewpagerHome.adapter = HomePagerStateAdapter(this@HomeActivity)
        TabLayoutMediator(tabHome, viewpagerHome) { tab, position ->
            tab.text = menuTabTitles.keys.toList()[position]
            tab.setIcon(menuTabTitles.values.toList()[position])
        }.attach()
    }
}