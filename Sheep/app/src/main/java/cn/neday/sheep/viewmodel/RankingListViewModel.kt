package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RankingListViewModel : BaseViewModel() {

    val mRankGoods: MutableLiveData<List<RankGoods>> = MutableLiveData()

    private val repository by lazy { GoodsRepository() }

    fun getRankingList(rankType: Int, cid: String) {
        launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.getRankingList(rankType, cid) }
                executeResponse(response, { mRankGoods.value = response.data }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}