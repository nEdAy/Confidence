package cn.neday.sheep.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
abstract class BaseDataSourceFactory<T> : DataSource.Factory<String, T>() {

    abstract val sourceLiveData: MutableLiveData<out BasePageKeyedDataSource<T>>
}
