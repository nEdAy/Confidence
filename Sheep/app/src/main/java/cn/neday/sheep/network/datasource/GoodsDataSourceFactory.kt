package cn.neday.sheep.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import cn.neday.sheep.model.Goods

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class GoodsDataSourceFactory(private val cid: String) : BaseDataSourceFactory<Goods>() {

    override val sourceLiveData = MutableLiveData<GoodsDataSource>()

    override fun create(): DataSource<String, Goods> {
        val source = GoodsDataSource(cid)
        sourceLiveData.postValue(source)
        return source
    }
}