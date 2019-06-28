package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.GoodsApi
import cn.neday.sheep.util.SignMD5Util
import java.util.*

/**
 * Goods Repository
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
    suspend fun getRankingList(rankType: Int, cid: String): Response<List<Goods>> {
        val parameterMap = TreeMap<String, Any>()
        parameterMap["rankType"] = rankType
        parameterMap["cid"] = cid
        return apiCall { goodsApi.rankingList(SignMD5Util.getSignParameterMap(parameterMap)) }
    }
}