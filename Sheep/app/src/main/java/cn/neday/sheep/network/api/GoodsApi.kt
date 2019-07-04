package cn.neday.sheep.network.api

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.model.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GoodsApi {

    @GET("goods/get-ranking-list")
    suspend fun rankingList(@QueryMap parameterMap: HashMap<String, Any>): Response<List<RankGoods>>

    @GET("goods/nine/op-goods-list")
    suspend fun nineOpGoodsList(@QueryMap parameterMap: HashMap<String, Any>): Response<Pages<List<Goods>>>
}
