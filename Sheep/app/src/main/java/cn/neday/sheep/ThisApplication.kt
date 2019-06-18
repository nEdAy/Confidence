package cn.neday.sheep

import android.app.Application
import com.tencent.bugly.Bugly

class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Bugly.init(applicationContext, "923c0825a2", false)
    }

    companion object {
        lateinit var instance: ThisApplication
    }
}