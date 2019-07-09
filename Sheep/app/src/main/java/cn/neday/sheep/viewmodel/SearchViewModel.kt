package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    val mHotWords: MutableLiveData<HotWords> = MutableLiveData()

    private val repository by lazy { CategoryRepository() }

    /**
     * 热搜记录
     * 该接口提供了昨日CMS端大淘客采集统计的前100名搜索热词
     */
    fun getTop100() {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getTop100()
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