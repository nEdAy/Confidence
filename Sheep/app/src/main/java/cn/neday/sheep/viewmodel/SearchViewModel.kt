package cn.neday.sheep.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.HistoryWords
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.model.database.AppDatabase
import cn.neday.sheep.model.repository.HistoryWordsRepository
import cn.neday.sheep.network.repository.CategoryRepository
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    val mHotWords: MutableLiveData<HotWords> = MutableLiveData()

    private val categoryRepository by lazy { CategoryRepository() }

    private val mHistoryWordsRepository: HistoryWordsRepository
    private val mHistoryWords: LiveData<List<HistoryWords>>

    init {
        val historyWordsDao = AppDatabase.getDatabase(Utils.getApp()).historyWordsDao()
        mHistoryWordsRepository = HistoryWordsRepository(historyWordsDao)
        mHistoryWords = mHistoryWordsRepository.allHistoryWords
    }

    /**
     * 热搜记录
     * 该接口提供了昨日CMS端大淘客采集统计的前100名搜索热词
     */
    fun getTop100() {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    categoryRepository.getTop100()
                }
                executeResponse(response, {
                    mHotWords.value = response.data
                }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}