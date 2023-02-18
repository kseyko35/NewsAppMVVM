package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.adapters.NewsAdapter
import com.kseyko.newsappmvvm.databinding.FragmentBreakingNewsBinding
import com.kseyko.newsappmvvm.ui.NewsActivity
import com.kseyko.newsappmvvm.ui.NewsViewModel
import com.kseyko.newsappmvvm.util.Resource


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var binding: FragmentBreakingNewsBinding? = null
    private var TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()
        setOnClickListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
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
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun hideProgressBar() {
        binding?.paginationProgressBar?.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding?.paginationProgressBar?.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding?.rvBreakingNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}