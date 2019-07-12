package cn.neday.sheep.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HistoryWords(
    @PrimaryKey @ColumnInfo(name = "key_words") val keyWords: String,
    @ColumnInfo(name = "search_time") val searchTime: Long
) : Comparator<HistoryWords> {
    override fun compare(p0: HistoryWords, p1: HistoryWords): Int {
        return ((p0.searchTime - p1.searchTime).toInt())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other is HistoryWords) {
            this.keyWords == other.keyWords
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = keyWords.hashCode()
        result *= 31
        return result
    }
}
