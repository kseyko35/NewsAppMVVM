package com.kseyko.newsappmvvm.repository

import com.kseyko.newsappmvvm.api.RetrofitInstance
import com.kseyko.newsappmvvm.db.ArticleDatabase


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      17,February,2023      ║
╚════════════════════════════╝
 */
class NewsRepository(val db: ArticleDatabase?) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)
}