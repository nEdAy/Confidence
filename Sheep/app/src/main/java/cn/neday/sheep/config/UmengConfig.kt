package cn.neday.sheep.config

import android.content.Context
import cn.neday.sheep.BuildConfig
import com.blankj.utilcode.util.LogUtils
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent


object UmengConfig {

    private const val UMENG_APP_KEY = "5d087b810cafb25c1d000e9f"
    private const val UMENG_PUSH_MESSAGE_SECRET = "5df990afe466c1bd066b06d3a2fd225d"

    fun init(applicationContext: Context) {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(
            applicationContext,
            UMENG_APP_KEY,
            "Channel", // TODO: chanel
            UMConfigure.DEVICE_TYPE_PHONE,
            UMENG_PUSH_MESSAGE_SECRET
        )
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(!BuildConfig.DEBUG)
        // 获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(applicationContext)
        // 注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtils.i("注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                LogUtils.e("注册失败：-------->  s:$s,s1:$s1")
            }
        })
    }
}