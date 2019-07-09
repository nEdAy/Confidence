package cn.neday.sheep.network.repository

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Listing
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.GoodsApi
import cn.neday.sheep.network.datasource.BaseDataSourceFactory
import cn.neday.sheep.network.datasource.GoodsDataSourceFactory
import cn.neday.sheep.network.datasource.GoodsSearchDataSourceFactory

/**
 * RankGoods Repository
 *
 * @author nEdAy
 */
class GoodsRepository : BaseRepository() {

    private val goodsApi: GoodsApi by lazy { RetrofitClient().getRetrofit(GoodsApi::class.java) }

    suspend fun getRankingList(rankType: Int, cid: String): Response<List<RankGoods>> {
        return apiCall { goodsApi.rankingList(rankType, cid) }
    }

    suspend fun getListSimilerGoodsByOpen(id: Int, size: Int): Response<List<Goods>> {
        return apiCall { goodsApi.listSimilerGoodsByOpen(id, size) }
    }

    suspend fun getListSuperGoods(
        type: Int, keyWords: String,
        tmall: Int, haitao: Int, sort: String
    ): Response<List<Goods>> {
        return apiCall { goodsApi.listSuperGoods(type, keyWords, tmall, haitao, sort) }
    }

    @MainThread
    fun getNineOpGoodsList(cid: String): Listing<Goods> {
        return makeListing(GoodsDataSourceFactory(cid))
    }

    @MainThread
    fun getDtkSearchGoods(keyWords: String): Listing<Goods> {
        return makeListing(GoodsSearchDataSourceFactory(keyWords))
    }

    private fun makeListing(sourceFactory: BaseDataSourceFactory<Goods>): Listing<Goods> {
        // We use toLiveData Kotlin extension function here, you could also use LivePagedListBuilder
        val livePagedList = sourceFactory.toLiveData(
            config = Config(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = true,
                initialLoadSizeHint = PAGE_SIZE * INITIAL_PAGE_MULTIPLIER
            )
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

    companion object {
        // 默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
        private const val PAGE_SIZE = 50
        private const val PREFETCH_DISTANCE = 20
        private const val INITIAL_PAGE_MULTIPLIER = 1
    }
}