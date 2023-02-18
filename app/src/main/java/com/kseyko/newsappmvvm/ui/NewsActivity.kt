package com.kseyko.newsappmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.databinding.ActivityNewsBinding
import com.kseyko.newsappmvvm.db.ArticleDatabase
import com.kseyko.newsappmvvm.repository.NewsRepository

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewModel()
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun setViewModel() {
        val repository = NewsRepository(ArticleDatabase.getInstance(this))
        val viewModelFactory = NewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory)[NewsViewModel::class.java]
    }
}