package cn.neday.sheep.activity

import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.config.HawkConfig.MOBILE
import cn.neday.sheep.config.HawkConfig.TOKEN
import cn.neday.sheep.config.UrlConfig
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ClearEditText
import cn.neday.sheep.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.blankj.utilcode.util.*
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

    private var mTimeCount: TimeCount? = null
    private var mIsVoiceVerifyCode = false
    private var mIsAgreeAgreement = true

    override val layoutId = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {
        registerSMSSDK()
        initTitleAndBackgroundByTime()
        initEditViewByLastMobile()
        iv_login_bg.setOnClickListener { KeyboardUtils.hideSoftInput(this) }
        btn_login.setOnClickListener { registerOrLogin() }
        tv_change_login_way.setOnClickListener { changeLoginWay() }
        tv_lostPassword.setOnClickListener { resetPassword() }
        tv_sms.setOnClickListener { requestVerificationCode() }
        iv_agreement.setOnClickListener { changeAgreementIv() }
        tv_agreement.setOnClickListener { AliTradeHelper(this).showItemURLPage(UrlConfig.KZ_YHSYXY) }
        mViewModel.user.observe(this, Observer {
            Hawk.put(TOKEN, it.token)
            Hawk.put(MOBILE, it.mobile)
            ActivityUtils.finishActivity(this)
        })
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    override fun onBackPressed() {
        if (mTimeCount != null) {
            val dialog = NormalDialog(this)
            dialog.content("验证码短信可能略有延迟，确定返回并重新开始？")
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23f)
                .show()
            dialog.setOnBtnClickL(
                OnBtnClickL { dialog.dismiss() },
                OnBtnClickL {
                    dialog.superDismiss()
                    ActivityUtils.finishActivity(this)
                })
        } else {
            super.onBackPressed()
        }
    }

    private fun initEditViewByLastMobile() {
        val mobile = Hawk.get<String>(MOBILE)
        if (!StringUtils.isTrimEmpty(mobile)) {
            et_mobile.setText(mobile)
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
        if (!mIsAgreeAgreement) {
            ToastUtils.showShort("您尚未同意《用户使用协议》")
            return
        }
        val mobile = et_mobile.text.toString().trim { it <= ' ' }.replace(" ", "")
        val password = et_password.text.toString().trim()
        val smsCode = et_sms.text.toString().trim()
        if (checkUserOk(mobile, password, smsCode)) {
            KeyboardUtils.hideSoftInput(this)
            mViewModel.registerOrLogin(mobile, password, smsCode, "")
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

    private fun requestVerificationCode() {
        val mobile = et_mobile.text.toString().trim { it <= ' ' }.replace(" ", "")
        if (TextUtils.isEmpty(mobile)) {
            shakeAnimationAndFocusUi(et_mobile, R.string.toast_error_phone_null)
            return
        }
        if (!RegexUtils.isMobileExact(mobile)) {
            shakeAnimationAndFocusUi(et_mobile, R.string.toast_error_phone_error)
            return
        }
        // 先短信验证码，闲置30s后切换语音验证码
        if (mIsVoiceVerifyCode) {
            val dialog = NormalDialog(this)
            dialog.content("确定后将致电您的手机号并语音播报验证码，如不希望被来点打扰请返回。")
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23f)
                .show()
            dialog.setOnBtnClickL(
                OnBtnClickL { dialog.dismiss() },
                OnBtnClickL {
                    dialog.dismiss()
                    SMSSDK.getVoiceVerifyCode(COUNTRY_NUMBER, mobile)
                })
        } else {
            SMSSDK.getVerificationCode(
                COUNTRY_NUMBER, mobile, "10085157"
            ) { _, _ -> true }
        }
    }

    private fun checkUserOk(mobile: String, password: String, smsCode: String): Boolean {
        if (TextUtils.isEmpty(mobile)) {
            shakeAnimationAndFocusUi(et_mobile, R.string.toast_error_phone_null)
            return false
        }
        if (!RegexUtils.isMobileExact(mobile)) {
            shakeAnimationAndFocusUi(et_mobile, R.string.toast_error_phone_error)
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

    private fun changeAgreementIv() {
        mIsAgreeAgreement = !mIsAgreeAgreement
        iv_agreement.setImageResource(if (mIsAgreeAgreement) R.drawable.check_normal else R.drawable.check_pressed)
    }

    private fun registerSMSSDK() {
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any?) {
                runOnUiThread {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        // 禁止点击获取验证码按钮和修改手机号
                        tv_sms.isClickable = false
                        et_mobile.isEnabled = false
                        when (event) {
                            SMSSDK.EVENT_GET_VERIFICATION_CODE -> {
                                // 短信验证 30s 倒计时
                                mTimeCount = TimeCount(30000, 1000)
                                mTimeCount?.start()
                                tx_hint_voice_verify_code.text = "我们已发送验证码短信到您的手机号"
                            }
                            SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE -> {
                                // 请求发送语音验证码，无返回
                                tx_hint_voice_verify_code.text = "我们正在致电语音播报验证码到您的手机号"
                                tv_sms.text = " 致电中 "
                            }
                        }
                    } else {
                        tv_sms.isEnabled = true
                        et_mobile.isEnabled = true
                        tv_sms.text = " 请重试 "
                        (data as Throwable).printStackTrace()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
        mTimeCount?.cancel()
    }

    internal inner class TimeCount(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        override fun onTick(millisUntilFinished: Long) {
            tv_sms.text = String.format(getString(R.string.countdown_number), millisUntilFinished / 1000)
        }

        override fun onFinish() {
            tv_sms.isClickable = true
            mIsVoiceVerifyCode = true
            tv_sms.text = " 发送语音验证 "
        }
    }

    companion object {

        private const val COUNTRY_NUMBER = "86"
    }
}
