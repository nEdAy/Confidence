package cn.neday.sheep.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cn.neday.sheep.model.HistoryWords

@Dao
interface HistoryWordsDao {
    @Query("SELECT * FROM historyWords")
    fun getAll(): LiveData<List<HistoryWords>>

    @Query("SELECT * FROM historyWords WHERE key_words LIKE :keyWords LIMIT 1")
    fun findByKeyWords(keyWords: String): HistoryWords

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: HistoryWords)

    @Delete
    fun delete(user: HistoryWords)

    @Query("DELETE FROM historyWords")
    fun deleteAll()
}