package cn.neday.sheep.network.api

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GoodsApi {

    @GET("v1/goods/ranking")
    suspend fun rankingList(@QueryMap parameterMap: HashMap<String, Any>): Response<List<Goods>>
}
