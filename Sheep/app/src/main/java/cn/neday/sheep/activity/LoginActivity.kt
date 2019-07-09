package cn.neday.sheep.activity

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.config.HawkConfig.TOKEN
import cn.neday.sheep.config.HawkConfig.USERNAME
import cn.neday.sheep.config.UrlConfig
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ClearEditText
import cn.neday.sheep.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.blankj.utilcode.util.*
import com.flyco.animation.BounceEnter.BounceTopEnter
import com.flyco.animation.SlideExit.SlideBottomExit
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

/**
 * 登录页
 *
 * @author nEdAy
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    private var timeCount: TimeCount? = null
    private var isVoiceVerifyCode = false
    private var isAgreeAgreement = true

    override val layoutId = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {
        registerSMSSDK()
        initTitleAndBackgroundByTime()
        initEditViewByLastUsername()
        iv_login_bg.setOnClickListener { KeyboardUtils.hideSoftInput(this) }
        btn_login.setOnClickListener { registerOrLogin() }
        tv_change_login_way.setOnClickListener { changeLoginWay() }
        tv_lostPassword.setOnClickListener { resetPassword() }
        tv_sms.setOnClickListener { requestVerificationCode() }
        iv_agreement.setOnClickListener { changeAgreementIv() }
        tv_agreement.setOnClickListener { AliTradeHelper(this).showItemURLPage(UrlConfig.KZ_YHSYXY) }
        mViewModel.mUser.observe(this, Observer {
            Hawk.put(TOKEN, it.token)
            Hawk.put(USERNAME, it.username)
            ActivityUtils.finishActivity(this)
        })
    }

    /**
     * 用户back按键回馈
     */
    override fun onBackPressed() {
        val dialog = NormalDialog(this)
        dialog.content("验证码短信可能略有延迟，确定返回并重新开始？")
            .style(NormalDialog.STYLE_TWO)
            .titleTextSize(23f)
            .showAnim(BounceTopEnter())
            .dismissAnim(SlideBottomExit())
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                dialog.superDismiss()
                finish()
            })
    }

    private fun initEditViewByLastUsername() {
        val username = Hawk.get<String>(USERNAME)
        if (!StringUtils.isTrimEmpty(username)) {
            et_username.setText(username)
            et_password.isFocusable = true
            et_password.isFocusableInTouchMode = true
            et_password.requestFocus()
            et_password.requestFocusFromTouch()
        }
    }

    private fun initTitleAndBackgroundByTime() {
        val isDayNotNight = TimeUtils.getValueByCalendarField(TimeUtils.getNowDate(), Calendar.HOUR_OF_DAY) in 8..20
        if (isDayNotNight) {
            iv_login_bg.setImageResource(R.drawable.good_morning_img)
            tv_login_title.text = getString(R.string.tx_login_title_day)
        } else {
            iv_login_bg.setImageResource(R.drawable.good_night_img)
            tv_login_title.text = getString(R.string.tx_login_title_night)
        }
    }

    private fun registerOrLogin() {
        if (!isAgreeAgreement) {
            ToastUtils.showShort("您尚未同意《用户使用协议》")
            return
        }
        val username = et_username.text.toString().trim { it <= ' ' }.replace(" ", "")
        val password = et_password.text.toString().trim()
        val smsCode = et_sms.text.toString().trim()
        if (checkUserOk(username, password, smsCode)) {
            KeyboardUtils.hideSoftInput(this)
            mViewModel.registerOrLogin(username, password, smsCode, "")
        }
    }

    private fun changeLoginWay() {
        if (tv_change_login_way.text == getString(R.string.tx_change_login_via_password)) {
            tv_change_login_way.text = getString(R.string.tx_change_login_via_sms_code)
            rl_sms.visibility = View.VISIBLE
            til_password.visibility = View.GONE
        } else {
            tv_change_login_way.text = getString(R.string.tx_change_login_via_password)
            til_password.visibility = View.VISIBLE
            rl_sms.visibility = View.GONE
        }
    }

    private fun resetPassword() {

    }

    /**
     * 重新请求短信验证码
     */
    private fun requestVerificationCode() {
        val username = et_username.text.toString().trim { it <= ' ' }.replace(" ", "")
        if (TextUtils.isEmpty(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_null)
            return
        }
        if (!RegexUtils.isMobileExact(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_error)
            return
        }
        // 先短信验证码，闲置30s后切换语音验证码
        if (isVoiceVerifyCode) {
            val dialog = NormalDialog(this)
            dialog.content("确定后将致电您的手机号并语音播报验证码，如不希望被来点打扰请返回。")
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23f)
                .showAnim(BounceTopEnter())
                .dismissAnim(SlideBottomExit())
                .show()
            dialog.setOnBtnClickL(
                OnBtnClickL { dialog.dismiss() },
                OnBtnClickL {
                    dialog.dismiss()
                        SMSSDK.getVoiceVerifyCode(COUNTRY_NUMBER, username)
                        tx_hint_voice_verify_code.text = "我们正在致电语音播报验证码到您的手机号"
                })
        } else {
                ToastUtils.showShort(R.string.network_tips)
        }
    }

    private fun checkUserOk(username: String, password: String, smsCode: String): Boolean {
        if (TextUtils.isEmpty(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_null)
            return false
        }
        if (!RegexUtils.isMobileExact(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_error)
            return false
        }
        if (til_password.visibility == View.VISIBLE && TextUtils.isEmpty(password)) {
            shakeAnimationAndFocusUi(et_password, R.string.toast_error_password_null)
            return false
        }
        if (til_password.visibility == View.VISIBLE && !CommonUtils.isValidPassword(password)) {
            shakeAnimationAndFocusUi(et_password, R.string.toast_error_password_error)
            return false
        }
        if (rl_sms.visibility == View.VISIBLE && TextUtils.isEmpty(smsCode)) {
            shakeAnimationAndFocusUi(et_sms, R.string.toast_error_sms_null)
            return false
        }
        if (rl_sms.visibility == View.VISIBLE && !CommonUtils.isValidSmsCode(smsCode)) {
            shakeAnimationAndFocusUi(et_sms, R.string.toast_error_sms_error)
            return false
        }
        return true
    }

    private fun shakeAnimationAndFocusUi(editTextView: ClearEditText, toastErrorMsgRes: Int) {
        ToastUtils.showShort(toastErrorMsgRes)
        editTextView.requestFocus()
        CommonUtils.setShakeAnimation(editTextView)
    }

    /**
     * 改变同意合同样式
     */
    private fun changeAgreementIv() {
        isAgreeAgreement = !isAgreeAgreement
        iv_agreement.setImageResource(if (isAgreeAgreement) R.drawable.check_normal else R.drawable.check_pressed)
    }

    private fun registerSMSSDK() {
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                runOnUiThread {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 禁止点击获取验证码按钮和修改手机号
                        tv_sms.isClickable = false
                        et_username.isEnabled = false
                        when (event) {
                            SMSSDK.EVENT_GET_VERIFICATION_CODE -> {
                                // 获取验证码成功
                                //val smart = data as Boolean
                                //if (smart) {
                                // 通过Mob云验证
                                // mViewModel.registerOrLogin("", "", "", "")
                                //} else {
                                // 依然走短信验证 30s 倒计时
                                timeCount = TimeCount(30000, 1000)
                                timeCount?.start()
                                tx_hint_voice_verify_code.text = "我们已发送验证码短信到您的手机号"
                                //}
                            }
                            SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE -> {
                                // 请求发送语音验证码，无返回
                                tv_sms.text = " 致电中 "
                            }
                        }
                    } else {
                        tv_sms.isEnabled = true
                        et_username.isEnabled = true
                        tv_sms.text = " 请重试 "
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
        timeCount?.cancel()
    }

    internal inner class TimeCount(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            tv_sms.text = String.format(getString(R.string.countdown_number), millisUntilFinished / 1000)
        }

        override fun onFinish() {
            tv_sms.isClickable = true
            isVoiceVerifyCode = true
            tv_sms.text = " 发送语音验证 "
        }
    }

    companion object {

        private const val COUNTRY_NUMBER = "86"
    }
}
