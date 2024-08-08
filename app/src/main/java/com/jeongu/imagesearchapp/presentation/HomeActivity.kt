package com.jeongu.imagesearchapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.tabs.TabLayoutMediator
import com.jeongu.imagesearchapp.R
import com.jeongu.imagesearchapp.databinding.ActivityHomeBinding
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkFragment
import com.jeongu.imagesearchapp.presentation.search.SearchFragment

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
//        bottomNavigationHome.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.navigation_search -> {
//                    SearchFragment().loadFragment()
//                    true
//                }
//                R.id.navigation_bookmark -> {
//                    BookmarkFragment().loadFragment()
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }
        viewpagerHome.adapter = HomePagerStateAdapter(this@HomeActivity)
        TabLayoutMediator(tabHome, viewpagerHome) { tab, position ->
            tab.text = menuTabTitles.keys.toList()[position]
            tab.setIcon(menuTabTitles.values.toList()[position])
        }.attach()
    }

//    private fun Fragment.loadFragment() {
//        supportFragmentManager.commit {
//            replace(R.id.container_home, this@loadFragment)
//            setReorderingAllowed(true)
//            addToBackStack(null)
//        }
//    }
}