package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.kseyko.newsappmvvm.R
import com.kseyko.newsappmvvm.databinding.FragmentArticleBinding
import com.kseyko.newsappmvvm.models.Article
import com.kseyko.newsappmvvm.ui.NewsActivity
import com.kseyko.newsappmvvm.ui.NewsViewModel


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var viewModel: NewsViewModel
    var binding: FragmentArticleBinding? = null
    private val TAG = "ArticleFragment"

    private val args: ArticleFragmentArgs by navArgs()
    private lateinit var article : Article
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        article = args.article

        setOnClickListener()

        binding?.webView?.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url.toString())
        }
    }

    private fun setOnClickListener() {
        binding?.fab?.setOnClickListener {
            viewModel.saveArticle(article)
            view?.let { view -> Snackbar.make(view, "Article saved Successfully", Snackbar.LENGTH_SHORT).show() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}