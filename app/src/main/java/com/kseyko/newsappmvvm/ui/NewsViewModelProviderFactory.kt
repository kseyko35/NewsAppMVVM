package com.kseyko.newsappmvvm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kseyko.newsappmvvm.repository.NewsRepository


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      17,February,2023      ║
╚════════════════════════════╝
 */
class NewsViewModelProviderFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}