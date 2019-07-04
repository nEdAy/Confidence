package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NineGoodsListViewModel : BaseViewModel() {

    val mGoods: MutableLiveData<List<Goods>> = MutableLiveData()
    val mPageId: MutableLiveData<String> = MutableLiveData()
    val mTotalNum: MutableLiveData<Int> = MutableLiveData()

    private val pageId: String = "1"
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

    companion object {
        // 默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
        private const val PAGE_SIZE = 10
        prefetchDistance
        private const val ENABLE_PLACEHOLDERS = false
    }

    fun getNineOpGoodsList(cid: String) {
        launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.getNineOpGoodsList(PAGE_SIZE, pageId, cid) }
                executeResponse(response, {
                    mGoods.value = response.data?.list
                    mTotalNum.value = response.data?.totalNum
                    mPageId.value = response.data?.pageId
                }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}