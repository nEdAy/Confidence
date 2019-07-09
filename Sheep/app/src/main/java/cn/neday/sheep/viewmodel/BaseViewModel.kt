package cn.neday.sheep.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.neday.sheep.R
import cn.neday.sheep.model.Response
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils.getString
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val mErrMsg: MutableLiveData<String> = MutableLiveData()

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        if (NetworkUtils.isAvailableByPing()) {
            launchOnUI {
                tryCatch(tryBlock, {}, {}, true)
            }
        } else {
            mErrMsg.value = getString(R.string.network_tips)
        }
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(
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