package com.kseyko.newsappmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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

@Database(entities = [Article::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase? {
            if (instance == null) {
                synchronized(ArticleDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ArticleDatabase::class.java, "article_db.db"
                    ).build()
                }
            }
            return instance
        }
    }



}