package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import cn.neday.sheep.network.repository.GoodsRepository

class NineGoodsListViewModel : BaseViewModel() {

    private val mRepository by lazy { GoodsRepository() }

    private val mCid = MutableLiveData<String>()

    private val mRepoResult = Transformations.map(mCid) {
        mRepository.getNineOpGoodsList(it)
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

    fun showNineOpGoodsList(cid: String) {
        mCid.value = cid
    }
}