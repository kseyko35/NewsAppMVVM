package com.kseyko.newsappmvvm.repository

import com.kseyko.newsappmvvm.api.RetrofitInstance
import com.kseyko.newsappmvvm.db.ArticleDatabase
import com.kseyko.newsappmvvm.models.Article


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      17,February,2023      ║
╚════════════════════════════╝
 */
class NewsRepository(private val db: ArticleDatabase?) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber )

    suspend fun insertArticle(article : Article) = db?.getArticleDao()?.insert(article)

    fun getSavedNews() = db?.getArticleDao()?.getAllArticle()

    suspend fun deleteArticle(article: Article) = db?.getArticleDao()?.deleteArticle(article)
}