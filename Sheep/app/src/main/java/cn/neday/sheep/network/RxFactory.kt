package cn.neday.sheep.network


import cn.neday.sheep.model.Response
import cn.neday.sheep.network.api.UserApi

/**
 * Api工厂
 *
 * @author nEdAy
 */
object RxFactory {
    private val mUserApi: UserApi by lazy { RetrofitClient().getRetrofit(UserApi::class.java) }

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }
}
