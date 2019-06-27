package cn.neday.sheep.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * 启动页
 *
 * @author nEdAy
 */
class SplashActivity : BaseActivity() {

    private lateinit var mDisposable: CompositeDisposable

    // 为保证启动速度，SplashActivity不要调用setContentView()方法
    override val layoutId: Int? = null

    override fun initView() {
        checkIntentAndIsTaskRoot()
        delayJumpPage()
    }

    private fun checkIntentAndIsTaskRoot() {
        if (!(intent != null && intent.extras != null) && !isTaskRoot) {
            ActivityUtils.finishActivity(this)
        }
    }

    private fun delayJumpPage() {
        mDisposable = CompositeDisposable()
        mDisposable.add(Observable.timer(SHOW_TIME_MIN, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { doStartActivity(checkIsFirstStartApp()) })
    }

    /**
     * 检测是否是第一次启动并指定跳转页
     *
     * @return 页面序数
     */
    private fun checkIsFirstStartApp(): JumpPage {
        val userFirst = Hawk.get("isFirstStartApp", true)
        return if (userFirst) {
            JumpPage.GO_GUIDE
        } else {
            JumpPage.GO_MAIN
        }
    }

    /**
     * 跳转指定Activity
     *
     * @param jumpPage 页面序数
     */
    private fun doStartActivity(jumpPage: JumpPage) {
        when (jumpPage) {
            JumpPage.GO_GUIDE -> ActivityUtils.finishActivity(GuideActivity::class.java)
            JumpPage.GO_MAIN -> ActivityUtils.startActivity(MainActivity::class.java)
        }
        ActivityUtils.finishActivity(this)
    }

    /**
     * 屏蔽物理返回按钮
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }

    companion object {
        // delay 233ms
        private const val SHOW_TIME_MIN = 233L
    }

    enum class JumpPage {
        // 导航页
        GO_GUIDE,
        // 主页面
        GO_MAIN
    }
}
