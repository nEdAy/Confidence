package cn.neday.sheep.network.repository

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.toLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.GoodsApi
import java.util.*
import java.util.concurrent.Executors

/**
 * RankGoods Repository
 *
 * @author nEdAy
 */
class GoodsRepository : BaseRepository() {

    private val goodsApi: GoodsApi by lazy { RetrofitClient().getRetrofit(GoodsApi::class.java) }

    /**
     * 各大榜单
     * 实时销量榜等特色榜单。该接口包含大淘客平台的实时销量榜全天销量榜及热推榜，接口实时更新，推荐最新的榜单商品
     *
     * @param rankType 榜单类型 是 Number 1.实时销量榜 2.全天销量榜 3.热推榜
     * @param cid 大淘客一级类目id 否 Number 实时销量榜和全天销量榜支持传入分类返回分类榜数据
     * @return 返回参数
     */
    suspend fun getRankingList(rankType: Int, cid: String): Response<List<RankGoods>> {
        val parameterMap = HashMap<String, Any>()
        parameterMap["rankType"] = rankType
        parameterMap["cid"] = cid
        return apiCall { goodsApi.rankingList(parameterMap) }
    }

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    @MainThread
    fun getNineOpGoodsList(pageSize: Int, cid: String): Listing<Goods> {
        val sourceFactory = GoodsDataSourceFactory(goodsApi, cid, NETWORK_IO)

        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            pageSize = pageSize,
            // provide custom executor for network requests, otherwise it will default to
            // Arch Components' IO pool which is also used for disk access
            fetchExecutor = NETWORK_IO
        )

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }
        return Listing(
            pagedList = livePagedList,
            networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                it.networkState
            },
            retry = {
                sourceFactory.sourceLiveData.value?.retryAllFailed()
            },
            refresh = {
                sourceFactory.sourceLiveData.value?.invalidate()
            },
            refreshState = refreshState
        )
    }
}