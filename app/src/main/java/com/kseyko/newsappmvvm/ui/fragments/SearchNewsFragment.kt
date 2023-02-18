package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.adapters.NewsAdapter
import com.kseyko.newsappmvvm.databinding.FragmentSearchNewsBinding
import com.kseyko.newsappmvvm.ui.NewsActivity
import com.kseyko.newsappmvvm.ui.NewsViewModel
import com.kseyko.newsappmvvm.util.Constants
import com.kseyko.newsappmvvm.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.kseyko.newsappmvvm.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var binding: FragmentSearchNewsBinding? = null
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)
        setupRecyclerView()

        viewModel = (activity as NewsActivity).viewModel

        var job : Job? = null

        binding?.etSearch?.addTextChangedListener {editableText->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editableText.let {
                    if(editableText.toString().isNotEmpty()){
                        viewModel.searchNews(editableText.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse?.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding?.rvSearchNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun hideProgressBar() {
        binding?.paginationProgressBar?.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding?.paginationProgressBar?.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}