package cn.neday.sheep.network.datasource

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.NetworkState
import cn.neday.sheep.network.api.GoodsApi
import kotlinx.coroutines.*

class GoodsDataSource(
    private val goodsApi: GoodsApi,
    private val cid: String
) : PageKeyedDataSource<String, Goods>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            ArchTaskExecutor.getIOThreadExecutor().execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Goods>
    ) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Goods>) {
        networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getNineOpGoodsList(params.requestedLoadSize, params.key, cid)
                }
                executeResponse(response, {
                    val data = response.data
                    val items = response.data?.list ?: emptyList()
                    retry = null
                    callback.onResult(items, data?.pageId)
                    networkState.postValue(NetworkState.LOADED)
                }, {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(
                        NetworkState.error("error code: ${response.code}, error message: ${response.msg}")
                    )
                })
            } catch (exception: Exception) {
                retry = {
                    loadAfter(params, callback)
                }
                networkState.postValue(NetworkState.error(exception.message ?: "unknown err"))
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Goods>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getNineOpGoodsList(params.requestedLoadSize, INITIAL_PAGE_ID, cid)
                }
                executeResponse(response, {
                    val data = response.data
                    val items = response.data?.list ?: emptyList()
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    // callback.onResult(items, 0, data!!.totalNum, null, data.pageId)
                    callback.onResult(items, null, data?.pageId)
                }, {
                    retry = {
                        loadInitial(params, callback)
                    }
                    networkState.postValue(
                        NetworkState.error("error code: ${response.code}, error message: ${response.msg}")
                    )
                })
            } catch (exception: Exception) {
                retry = {
                    loadInitial(params, callback)
                }
                val error = NetworkState.error(exception.message ?: "unknown error")
                networkState.postValue(error)
                initialLoad.postValue(error)
            }
        }
    }

    private suspend fun getNineOpGoodsList(pageSize: Int, pageId: String, cid: String): Response<Pages<Goods>> {
        return apiCall { goodsApi.nineOpGoodsList(pageSize, pageId, cid) }
    }

    private suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }

    private suspend fun executeResponse(
        response: Response<Any>, successBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (response.code != 0) {
                errorBlock()
            } else {
                successBlock()
            }
        }
    }

    companion object {
        private const val INITIAL_PAGE_ID: String = "1"
    }
}