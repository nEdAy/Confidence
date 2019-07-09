package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.repository.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchResultViewModel : BaseViewModel() {

    val mGoods: MutableLiveData<List<Goods>> = MutableLiveData()

    private val goodsRepository by lazy { GoodsRepository() }

    /**
     * 超级搜索
     * 通过关键字进行搜索，提供大淘客商品库的精准搜索结果及联盟的相关商品结果，为您带来优质的搜索体验，可用在CMS/APP的搜索引擎
     *
     * @param type 搜索类型 0-综合结果，1-大淘客商品，2-联盟商品
     * @param keyWords 关键词搜索
     * @param tmall 是否天猫商品 1-天猫商品，0-所有商品，不填默认为0
     * @param haitao 是否海淘商品	 1-海淘商品，0-所有商品，不填默认为0
     * @param sort 排序字段信息 销量（total_sales） 价格（price），排序_des（降序），排序_asc（升序）
     */
    fun getListSuperGoods(type: Int, keyWords: String, tmall: Int, haitao: Int, sort: String) {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    goodsRepository.getListSuperGoods(type, keyWords, tmall, haitao, sort)
                }
                executeResponse(response, {
                    mGoods.value = response.data
                }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}