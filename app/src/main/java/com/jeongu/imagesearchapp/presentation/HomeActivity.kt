package com.jeongu.imagesearchapp.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.jeongu.imagesearchapp.R
import com.jeongu.imagesearchapp.databinding.ActivityHomeBinding
import com.jeongu.imagesearchapp.presentation.bookmark.BookmarkFragment
import com.jeongu.imagesearchapp.presentation.search.SearchFragment

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }
        initView()
    }

    private fun initView() = with(binding) {
        bottomNavigationHome.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_search -> {
                    SearchFragment().loadFragment()
                    true
                }
                R.id.navigation_bookmark -> {
                    BookmarkFragment().loadFragment()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    private fun Fragment.loadFragment() {
        supportFragmentManager.commit {
            replace(R.id.container_home, this@loadFragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }
}