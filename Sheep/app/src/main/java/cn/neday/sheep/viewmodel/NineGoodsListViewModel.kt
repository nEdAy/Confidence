package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cn.neday.sheep.network.repository.GoodsRepository

class NineGoodsListViewModel : BaseViewModel() {

    private val mRepository by lazy { GoodsRepository() }

    private val mCid = MutableLiveData<String>()

    private val mRepoResult = Transformations.map(mCid) {
        mRepository.getNineOpGoodsList(PAGE_SIZE, it)
    }

    val posts = Transformations.switchMap(mRepoResult) { it.pagedList }
    val networkState = Transformations.switchMap(mRepoResult) { it.networkState }
    val refreshState = Transformations.switchMap(mRepoResult) { it.refreshState }

    fun refresh() {
        mRepoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = mRepoResult.value
        listing?.retry?.invoke()
    }

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
    }

    fun showNineOpGoodsList(cid: String) {
        mCid.value = cid
    }
}