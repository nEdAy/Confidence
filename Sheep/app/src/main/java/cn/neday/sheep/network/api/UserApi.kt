package cn.neday.sheep.network.api


import cn.neday.sheep.model.Response
import cn.neday.sheep.model.User
import cn.neday.sheep.network.repository.UserRepository
import retrofit2.http.*

/**
 * User Api
 *
 * @author nEdAy
 */
interface UserApi {

    /**
     * 注册
     *
     * @param login   用户信息（帐号，密码MD5，短信验证码，邀请码）
     * @return 用户信息（token、id等）
     */
    @POST("v1/register")
    suspend fun register(@Body login: UserRepository.RegisterModel): Response<User>

    /**
     * 登录
     *
     * @param loginModel 用户信息（帐号，密码MD5）
     * @return 用户信息（token、id等）
     */
    @POST("v1/login")
    suspend fun login(@Body loginModel: UserRepository.LoginModel): Response<User>

    /**
     * 获取指定条件用户信息
     *
     * @param options 参数
     * @return 用户信息
     */
    @GET("v1/user")
    suspend fun queryUser(@QueryMap options: Map<String, Any>): Response<User>

    /**
     * 获取指定objectId用户全部信息
     *
     * @param objectId 用户ID
     * @return 用户信息
     */
    @GET("users/{objectId}")
    suspend fun getUser(@Path("objectId") objectId: String): Response<User>

    /**
     * 获取指定objectId用户的某个字段信息（返回用户积分信息||返回用户摇一摇信息||返回用户签到信息）
     *
     * @param objectId 用户ID
     * @param include  指定返回字段
     * @return 对应信息
     */
    @GET("users/{objectId}")
    suspend fun getUser(@Path("objectId") objectId: String, @Query("include") include: String): Response<User>

    /**
     * 更新用户 需要Session-Token  username和password可以更改，但是新的username不能重复
     *
     * @param objectId 用户ID
     * @param user     需要更新的用户信息
     * @return 回调信息 "updatedAt": YYYY-mm-dd HH:ii:ss
     */
    @PUT("users/{objectId}")
    suspend fun updateUser(@Path("objectId") objectId: String, @Body user: User): Response<User>

    /**
     * 忘记密码
     *
     * @param username 用户名
     * @param password 新密码
     * @param code     验证码
     * @return 回调信息
     */
    @PUT("users")
    suspend fun resetUserPassword(
        @Query("username") username: String,
        @Query("password") password: String, @Query("code") code: String
    ): Response<User>

    /**
     * 重置密码
     *
     * @param objectId    用户Id
     * @param oldPassword 老密码(MD5后)
     * @param newPassword 新密码(MD5后)
     * @return 回调信息
     */
    @PUT("users/{objectId}")
    suspend fun updateUserPassword(
        @Path("objectId") objectId: String,
        @Query("oldPassword") oldPassword: String, @Query("newPassword") newPassword: String
    ): Response<User>

//    /**
//     * 签到
//     *
//     * @param objectId 用户Id
//     * @param type     签到类型
//     * @return 签到记录
//     */
//    @GET("signIn/{objectId}")
//    suspend fun signIn(@Path("objectId") objectId: String, @Query("type") type: Int?): Observable<CreditsHistory>
//
//    /**
//     * 摇一摇
//     *
//     * @param objectId 用户Id
//     * @return 摇一摇获得的积分变化值
//     */
//    @GET("shake/{objectId}")
//    suspend fun shake(@Path("objectId") objectId: String): Observable<Int>
}
