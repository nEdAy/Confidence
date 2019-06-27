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

    suspend fun login(username: String, passwordMD5: String): Response<User> {
        return apiCall { userApi.login(LoginModel(username, passwordMD5)) }
    }

    suspend fun register(username: String, passwordMD5: String, smsCode: String, inviteCode: String): Response<User> {
        return apiCall { userApi.register(RegisterModel(username, passwordMD5, smsCode, inviteCode)) }
    }

    class LoginModel(username: String, passwordMD5: String)

    class RegisterModel(username: String, passwordMD5: String, smsCode: String, inviteCode: String)
}