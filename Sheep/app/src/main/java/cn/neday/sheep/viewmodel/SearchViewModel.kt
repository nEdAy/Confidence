package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.config.HawkConfig
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class SearchViewModel : BaseViewModel() {

    val mHotWords: MutableLiveData<HotWords> = MutableLiveData()
    val mHistoryWords: MutableLiveData<LinkedHashSet<String>> = MutableLiveData()

    private val categoryRepository by lazy { CategoryRepository() }

    /**
     * 热搜记录
     * 该接口提供了昨日CMS端大淘客采集统计的前100名搜索热词
     */
    fun getHotWords() {
        val hotWords: HotWords? = Hawk.get(HawkConfig.HOTWORDS)
        if (hotWords != null) {
            mHotWords.value = hotWords
        }
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    categoryRepository.getTop100()
                }
                executeResponse(response, {
                    mHotWords.value = response.data
                    Hawk.put(HawkConfig.HOTWORDS, response.data)
                }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    fun getHistoryWords() {
        val historyWordsJson: String? = Hawk.get(HawkConfig.HISTORY_WORDS)
        mHistoryWords.value = Gson().fromJson(historyWordsJson, object : TypeToken<LinkedHashSet<String>>() {}.type)
    }

    fun cleanHistoryWords() {
        mHistoryWords.value = null
        Hawk.delete(HawkConfig.HISTORY_WORDS)
    }
}