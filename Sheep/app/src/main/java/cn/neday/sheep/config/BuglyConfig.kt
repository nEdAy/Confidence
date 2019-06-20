package cn.neday.sheep.config

import android.content.Context
import cn.neday.sheep.BuildConfig
import com.tencent.bugly.Bugly

object BuglyConfig {

    private const val BUGLY_APP_ID = "923c0825a2"

    fun init(applicationContext: Context) {
        Bugly.init(applicationContext, BUGLY_APP_ID, BuildConfig.DEBUG)
    }
}