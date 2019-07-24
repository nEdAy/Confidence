package cn.neday.sheep.network.repository

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.Config
import androidx.paging.toLiveData
import cn.neday.sheep.model.*
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

    suspend fun getNineOpGoodsList(pageSize: Int, pageId: String, cid: String): Response<Pages<Goods>> {
        return apiCall { goodsApi.nineOpGoodsList(pageSize, pageId, cid) }
    }

    suspend fun getDtkSearchGoods(pageSize: Int, pageId: String, keyWords: String): Response<Pages<Goods>> {
        return apiCall { goodsApi.getDtkSearchGoods(pageSize, pageId, keyWords) }
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

    companion object {
        // 默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
        const val PAGE_SIZE = 50
        const val PREFETCH_DISTANCE = 20
        const val INITIAL_PAGE_MULTIPLIER = "1"
    }
}