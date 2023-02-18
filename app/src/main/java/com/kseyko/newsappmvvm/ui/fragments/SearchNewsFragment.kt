package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.adapters.NewsAdapter
import com.kseyko.newsappmvvm.databinding.FragmentSearchNewsBinding
import com.kseyko.newsappmvvm.ui.NewsActivity
import com.kseyko.newsappmvvm.ui.NewsViewModel
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
    private val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchNewsBinding.bind(view)
        setRecyclerView()
        setOnClickListener()
        observeLiveData()

        viewModel = (activity as NewsActivity).viewModel

        var job: Job? = null
        binding?.etSearch?.addTextChangedListener { editableText ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editableText.let {
                    if (editableText.toString().isNotEmpty()) {
                        viewModel.searchNews(editableText.toString())
                    }
                }
            }
        }
    }

    private fun observeLiveData() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
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
        }
    }

    private fun setOnClickListener() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply { putSerializable("article", it) }
            findNavController().navigate(R.id.action_searchNewsFragment_to_articleFragment, bundle)
        }
    }

    private fun setRecyclerView() {
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