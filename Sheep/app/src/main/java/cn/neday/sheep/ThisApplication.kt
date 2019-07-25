package cn.neday.sheep

import android.app.Application
import cn.neday.sheep.config.BuglyConfig
import cn.neday.sheep.config.CalligraphyConfig
import cn.neday.sheep.config.UmengConfig
import cn.neday.sheep.util.AliTradeHelper
import com.blankj.utilcode.util.ProcessUtils
import com.didichuxing.doraemonkit.DoraemonKit
import com.mob.MobSDK
import com.orhanobut.hawk.Hawk


/**
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * 自定义全局Application类
 *
 * @author nEdAy
 */
class ThisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        CalligraphyConfig.init()
        UmengConfig.init(instance)
        if (!ProcessUtils.isMainProcess()) {
            return
        }
        DoraemonKit.install(instance)
        BuglyConfig.init(instance)
        MobSDK.init(instance)
        Hawk.init(instance).build()
        AliTradeHelper.asyncInit(instance)
    }

    companion object {
        lateinit var instance: ThisApplication
    }
}