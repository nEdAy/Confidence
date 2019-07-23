package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Response
import cn.neday.sheep.model.User
import cn.neday.sheep.network.RetrofitClient
import cn.neday.sheep.network.api.UserApi

/**
 * User Repository
 *
 * @author nEdAy
 */
class UserRepository : BaseRepository() {

    private val userApi: UserApi by lazy { RetrofitClient().getRetrofit(UserApi::class.java) }

    suspend fun registerOrLogin(mobile: String, passwordMD5: String, smsCode: String, inviteCode: String)
            : Response<User> {
        val map = HashMap<String, String>()
        map["mobile"] = mobile
        map["password"] = passwordMD5
        map["smsCode"] = smsCode
        map["inviteCode"] = inviteCode
        return apiCall { userApi.registerOrLogin(map) }
    }
}