package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.HotWords
import cn.neday.sheep.network.repository.CategoryRepository
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    val mHotWords: MutableLiveData<HotWords> = MutableLiveData()

    val mGoods: MutableLiveData<List<Goods>> = MutableLiveData()

    private val categoryRepository by lazy { CategoryRepository() }

    private val goodsRepository by lazy { GoodsRepository() }

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

    /**
     * 超级搜索
     * 通过关键字进行搜索，提供大淘客商品库的精准搜索结果及联盟的相关商品结果，为您带来优质的搜索体验，可用在CMS/APP的搜索引擎
     */
    fun getListSuperGoods(type: Int, keyWords: String, tmall: Int, haitao: Int, sort: String) {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    goodsRepository.getListSuperGoods(type, keyWords, tmall, haitao, sort)
                }
                executeResponse(response, {
                    mGoods.value = response.data
                }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}