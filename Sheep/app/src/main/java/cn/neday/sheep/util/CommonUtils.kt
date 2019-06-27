package cn.neday.sheep.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import cn.neday.sheep.R
import com.blankj.utilcode.util.ToastUtils

import java.math.BigDecimal

/**
 * 常用工具
 *
 * @author nEdAy
 */
object CommonUtils {

    /**
     * 去除数字里多余的0
     */
    fun getPrettyNumber(number: Double): String {
        return BigDecimal.valueOf(number).stripTrailingZeros().toPlainString()
    }

    /**
     * 检验密码长度是否正确
     */
    fun isValidPassword(target: CharSequence?): Boolean {
        return target != null && target.length > 5 && target.length < 17
    }

    /**
     * 指定View显示一个动画,抖5下
     *
     * @param view 指定的View
     */
    fun setShakeAnimation(view: View) {
        val translateAnimation = TranslateAnimation(0f, 10f, 0f, 10f)
        translateAnimation.interpolator = CycleInterpolator(5f)//抖5下
        translateAnimation.duration = 1000
        view.startAnimation(translateAnimation)
    }

    /**
     * 启动到app应用商店详情界面
     *
     *
     * 主流应用商店对应的包名如下：
     * com.qihoo.appstore  360手机助手
     * com.taobao.appcenter    淘宝手机助手
     * com.tencent.android.qqdownloader    应用宝
     * com.hiapk.marketpho 安卓市场
     * cn.goapk.market 安智市场
     *
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    fun launchAppDetail(mContext: Context?, marketPkg: String?) {
        try {
            val uri = Uri.parse("market://details?id=" + mContext?.packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            mContext?.startActivity(intent)
        } catch (e: Exception) {
            ToastUtils.showLong(mContext?.getString(R.string.tx_not_found_app_market))
        }

    }

    /**
     * 发起添加群流程。群号：神马快爆15群(109339243) 的 key 为： seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg
     * 调用 joinQQGroup(seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg) 即可发起手Q客户端申请加群 神马快爆15群(109339243)
     *
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     */
    fun joinQQGroup(activity: Activity): Boolean {
        return try {
            activity.startActivity(Intent().apply {
                data =
                    Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "seYgbMRGSIw_QOjspDBVp-1r1GTUGHCg")
                // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
                // addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            true
        } catch (e: Exception) {
            // 未安装手Q或安装的版本不支持
            false
        }
    }
}
