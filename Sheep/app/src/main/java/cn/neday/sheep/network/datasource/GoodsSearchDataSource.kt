package cn.neday.sheep.network.datasource

import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.NetworkState
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.GoodsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoodsSearchDataSource(private val keyWords: String) : BasePageKeyedDataSource<Goods>() {

    private val goodsApi: GoodsApi by lazy { RetrofitClient().getRetrofit(GoodsApi::class.java) }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Goods>) {
        networkState.postValue(NetworkState.LOADING)
        GlobalScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getDtkSearchGoods(params.requestedLoadSize, params.key, keyWords)
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
                    getDtkSearchGoods(params.requestedLoadSize, INITIAL_PAGE_ID, keyWords)
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


    private suspend fun getDtkSearchGoods(pageSize: Int, pageId: String, keyWords: String): Response<Pages<Goods>> {
        return apiCall { goodsApi.getDtkSearchGoods(pageSize, pageId, keyWords) }
    }
}