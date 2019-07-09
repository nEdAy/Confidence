package cn.neday.sheep.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryWords(
    @PrimaryKey @ColumnInfo(name = "key_words") val keyWords: String,
    @ColumnInfo(name = "search_time") val searchTime: String
)
