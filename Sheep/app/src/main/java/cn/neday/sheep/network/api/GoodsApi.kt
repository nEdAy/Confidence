package cn.neday.sheep.network.api

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*

interface GoodsApi {

    @GET("v1/goods/ranking")
    suspend fun rankingList(@QueryMap signParameterMap: SortedMap<String, Any>): Response<List<Goods>>
}
