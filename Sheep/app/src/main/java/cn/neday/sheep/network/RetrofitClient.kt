package cn.neday.sheep.network


import cn.neday.sheep.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 获取Retrofit实例
 *
 * @author nEdAy
 */
class RetrofitClient {

    fun <T> getRetrofit(clazz: Class<T>): T {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(clazz)
    }

    private val okHttpClient: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(loggingInterceptor)
                .connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
            return builder.build()
        }

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            }
            return httpLoggingInterceptor
        }

    companion object {
        private const val BASE_URL = "https://api.neday.cn/api/v1/"
        private const val TIME_OUT = 5
    }
}
