package com.kseyko.newsappmvvm.db

import androidx.room.TypeConverter
import com.kseyko.newsappmvvm.models.Source


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,February,2023      ║
╚════════════════════════════╝
 */
class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name,name)
    }
}