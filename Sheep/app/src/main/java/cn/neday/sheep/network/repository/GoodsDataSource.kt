package cn.neday.sheep.network.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.api.GoodsApi
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.Executor

class GoodsDataSource(
    private val goodsApi: GoodsApi,
    private val cid: String,
    private val retryExecutor: Executor
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
            retryExecutor.execute {
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
                    getNineOpGoodsList(params.requestedLoadSize, params.key, "-1")
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

    private val initialPageId: String = "1"

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
                    getNineOpGoodsList(params.requestedLoadSize, initialPageId, cid)
                }
                executeResponse(response, {
                    val data = response.data
                    val items = response.data?.list ?: emptyList()
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(items, "", data?.pageId)
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

    /**
     * 9.9精选
     * 大淘客专业选品团队提供的9.9精选商品，提供最优质的白菜商品列表，可组建9.9商品专区，提供丰富的选品体验
     *
     * @param pageSize 每页条数	是	Number	默认100 ，可选范围：10,50,100,200，如果小于10按10处理，大于200按照200处理，其它非范围内数字按100处理
     * @param pageId 分页id	是	String	默认为1，支持传统的页码分页方式和scroll_id分页方式，根据用户自身需求传入值。示例1：商品入库，则首次传入1，后续传入接口返回的pageId，接口将持续返回符合条件的完整商品列表，该方式可以避免入口商品重复；示例2：根据pageSize和totalNum计算出总页数，按照需求返回指定页的商品（该方式可能在临近页取到重复商品）
     * @param cid 一级类目Id	是	String	大淘客的一级分类id，如果需要传多个，以英文逗号相隔，如：”1,2,3”。一级分类id请求详情：-1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
     * @return 返回参数
     */
    private suspend fun getNineOpGoodsList(pageSize: Int, pageId: String, cid: String): Response<Pages<Goods>> {
        val parameterMap = HashMap<String, Any>()
        parameterMap["pageSize"] = pageSize
        parameterMap["initialPageId"] = pageId
        parameterMap["cid"] = cid
        return apiCall { goodsApi.nineOpGoodsList(parameterMap) }
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
}