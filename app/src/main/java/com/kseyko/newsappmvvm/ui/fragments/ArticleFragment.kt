package com.kseyko.newsappmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kseyko.newsappmvvm.R
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

    private lateinit var viewModel : NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as NewsActivity).viewModel
    }
}