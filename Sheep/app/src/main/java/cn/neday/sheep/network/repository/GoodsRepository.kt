package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.GoodsApi
import java.util.*

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

    /**
     * 9.9精选
     * 大淘客专业选品团队提供的9.9精选商品，提供最优质的白菜商品列表，可组建9.9商品专区，提供丰富的选品体验
     *
     * @param pageSize 每页条数	是	Number	默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
     * @param pageId 分页id	是	String	默认为1，支持传统的页码分页方式和scroll_id分页方式，根据用户自身需求传入值。示例1：商品入库，则首次传入1，后续传入接口返回的pageid，接口将持续返回符合条件的完整商品列表，该方式可以避免入口商品重复；示例2：根据pagesize和totalNum计算出总页数，按照需求返回指定页的商品（该方式可能在临近页取到重复商品）
     * @param cid 一级类目Id	是	String	大淘客的一级分类id，如果需要传多个，以英文逗号相隔，如：”1,2,3”。一级分类id请求详情：-1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
     * @return 返回参数
     */
    suspend fun getNineOpGoodsList(pageSize: Int, pageId: String, cid: String): Response<Pages<List<Goods>>> {
        val parameterMap = HashMap<String, Any>()
        parameterMap["pageSize"] = pageSize
        parameterMap["pageId"] = pageId
        parameterMap["cid"] = cid
        return apiCall { goodsApi.nineOpGoodsList(parameterMap) }
    }
}