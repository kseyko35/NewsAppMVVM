package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.adapters.NewsAdapter
import com.kseyko.newsappmvvm.databinding.FragmentSavedNewsBinding
import com.kseyko.newsappmvvm.ui.NewsActivity
import com.kseyko.newsappmvvm.ui.NewsViewModel
import androidx.recyclerview.widget.ItemTouchHelper


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var binding: FragmentSavedNewsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel

        setRecyclerView()
        setOnClickListener()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getSavedNews()?.observe(viewLifecycleOwner) { articles ->
            newsAdapter.differ.submitList(articles)
        }
    }

    private fun setOnClickListener() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_savedNewsFragment_to_articleFragment, bundle)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                view?.let {
                    Snackbar.make(it, "Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                        setAction("Undo"){
                            viewModel.saveArticle(article)
                        }.show()
                    }
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding?.rvSavedNews)
        }


    }

    private fun setRecyclerView() {
        newsAdapter = NewsAdapter()
        binding?.rvSavedNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}