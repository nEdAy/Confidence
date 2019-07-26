package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Banner
import cn.neday.sheep.model.RankingGoods
import cn.neday.sheep.network.repository.BannerRepository
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IndexViewModel : BaseViewModel() {

    private val bannerRepository by lazy { BannerRepository() }
    private val goodsRepository by lazy { GoodsRepository() }

    val banners: MutableLiveData<List<Banner>> = MutableLiveData()
    val rankGoods: MutableLiveData<List<RankingGoods>> = MutableLiveData()

    fun getBannerList() {
        launch {
            val response = withContext(Dispatchers.IO) { bannerRepository.getBannerList() }
            executeResponse(response, { banners.value = response.data }, { errMsg.value = response.msg })
        }
    }

    fun getRankingList(rankType: Int, cid: String = "") {
        launch {
            val response = withContext(Dispatchers.IO) { goodsRepository.getRankingList(rankType, cid) }
            executeResponse(response, { rankGoods.value = response.data }, { errMsg.value = response.msg })
        }
    }
}