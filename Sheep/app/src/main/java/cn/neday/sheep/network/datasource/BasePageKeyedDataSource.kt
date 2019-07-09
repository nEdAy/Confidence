package cn.neday.sheep.network.datasource

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import cn.neday.sheep.model.Response
import cn.neday.sheep.network.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

abstract class BasePageKeyedDataSource<T> : PageKeyedDataSource<String, T>() {

    // keep a function reference for the retry event
    protected var retry: (() -> Any)? = null

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
        callback: LoadCallback<String, T>
    ) {
        // ignored, since we only ever append to our initial load
    }

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }

    protected suspend fun executeResponse(
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
        const val INITIAL_PAGE_ID: String = "1"
    }
}