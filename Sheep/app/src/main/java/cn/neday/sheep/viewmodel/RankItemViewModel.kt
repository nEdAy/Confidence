package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RankItemViewModel : BaseViewModel() {

    val mRankGoods: MutableLiveData<List<Goods>> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy { GoodsRepository() }

//    val rankGoods = LivePagedListBuilder(
//        mRankGoods, PagedList.Config.Builder()
//            // 配置分页加载的数量
//            .setPageSize(PAGE_SIZE)
//            // 配置是否启动PlaceHolders
//            .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
//            // 初始化加载的数量
//            .setInitialLoadSizeHint(PAGE_SIZE)
//            .build()
//    ).build()


    fun getRankingList(rankType: Int, cid: String?) {
        launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.getRankingList(rankType, cid) }
                executeResponse(response, { mRankGoods.value = response.data }, { errMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    companion object {

        private const val PAGE_SIZE = 15

        private const val ENABLE_PLACEHOLDERS = false
    }
}