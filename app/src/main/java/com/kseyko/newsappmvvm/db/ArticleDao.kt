package com.kseyko.newsappmvvm.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kseyko.newsappmvvm.models.Article


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticle() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}