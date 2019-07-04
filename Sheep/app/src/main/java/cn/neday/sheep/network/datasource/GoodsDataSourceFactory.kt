package cn.neday.sheep.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.api.GoodsApi

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class GoodsDataSourceFactory(
    private val goodsApi: GoodsApi,
    private val cid: String
) : DataSource.Factory<String, Goods>() {

    val sourceLiveData = MutableLiveData<GoodsDataSource>()

    override fun create(): DataSource<String, Goods> {
        val source = GoodsDataSource(goodsApi, cid)
        sourceLiveData.postValue(source)
        return source
    }
}
