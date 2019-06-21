package cn.neday.sheep.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.umeng.analytics.MobclickAgent
import com.umeng.message.PushAgent

/**
 * Activity基类
 *
 * @author nEdAy
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AlibcTradeSDK.destory()
    }
}