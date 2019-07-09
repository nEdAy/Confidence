package cn.neday.sheep.model.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import cn.neday.sheep.model.HistoryWords
import cn.neday.sheep.model.dao.HistoryWordsDao

class HistoryWordsRepository(private val historyWordsDao: HistoryWordsDao) {

    val allHistoryWords: LiveData<List<HistoryWords>> = historyWordsDao.getAll()

    @WorkerThread
    suspend fun insert(historyWords: HistoryWords) {
        historyWordsDao.insertAll(historyWords)
    }
}
