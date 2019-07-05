package cn.neday.sheep.activity

import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.config.HawkConfig.TOKEN
import cn.neday.sheep.config.HawkConfig.USERNAME
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ClearEditText
import cn.neday.sheep.viewmodel.LoginViewModel
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.blankj.utilcode.util.*
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


/**
 * 登录页
 *
 * @author nEdAy
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    override val layoutId = R.layout.activity_login

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun initView() {
        registerSMSSDK()
        initTitleAndBackgroundByTime()
        initEditViewByLastUsername()
        iv_login_bg.setOnClickListener { KeyboardUtils.hideSoftInput(this) }
        btn_login.setOnClickListener { login() }
        btn_register.setOnClickListener { register() }
        tv_change_login_way.setOnClickListener { changeLoginWay() }
        tv_lostPassword.setOnClickListener { resetPassword() }
        tv_sms.setOnClickListener { requestVerificationCode() }
//        ss_login.setOnSelectedChangeListener {
//
//        }
        mViewModel.mUser.observe(this, Observer {
            Hawk.put(TOKEN, it.token)
            Hawk.put(USERNAME, it.username)
            ActivityUtils.finishActivity(this)
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

    private fun login() {
        val username = et_username.text.toString().trim { it <= ' ' }.replace(" ", "")
        val password = et_password.text.toString().trim()
        if (checkUserOk(username, password)) {
            KeyboardUtils.hideSoftInput(this)
            if (NetworkUtils.isAvailable()) {
                mViewModel.login(username, password)
            } else {
                ToastUtils.showShort(R.string.network_tips)
            }
        }
    }

    private fun register() {
        val username = et_username.text.toString().trim { it <= ' ' }.replace(" ", "")
        val password = et_password.text.toString().trim()
        if (checkUserOk(username, password)) {
            KeyboardUtils.hideSoftInput(this)
            if (NetworkUtils.isAvailable()) {
                mViewModel.register(username, password, "", "")
            } else {
                ToastUtils.showShort(R.string.network_tips)
            }
        }

        // getVoiceVerifyCode
    }

    private fun changeLoginWay() {
        if (btn_login.visibility == View.VISIBLE) {
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
    }

    private fun resetPassword() {

    }

    /**
     * 重新请求短信验证码
     */
    private fun requestVerificationCode() {
        // 先短信验证码，闲置30s后切换语音验证码
//        if (isVoice) {
//            val dialog = NormalDialog(mContext)
//            dialog.content("确定后将致电您的手机号并语音播报验证码，如不希望被来点打扰请返回。")
//                .style(NormalDialog.STYLE_TWO)
//                .titleTextSize(23f)
//                .showAnim(BounceTopEnter())
//                .dismissAnim(SlideBottomExit())
//                .show()
//            dialog.setOnBtnClickL(
//                OnBtnClickL { dialog.dismiss() },
//                {
//                    dialog.dismiss()
//                    if (!CommonUtils.isNetworkAvailable()) {
//                        CommonUtils.showToast(R.string.network_tips)
//                    } else {
//                        SMSSDK.getVoiceVerifyCode(country, phone)
//                        tv_top_message.setText("我们正在致电语音播报验证码到您的手机号")
//                    }
//                })
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SMSSDK.unregisterAllEventHandler()
    }

//        toSubscribe(RxFactory.getUserServiceInstance(null)
//            .login(phone, StringUtils.getMD5(password)),
//            { catLoadingView.show(supportFragmentManager, TAG) },
//            { user ->
//                catLoadingView.dismissAllowingStateLoss()
//                sharedPreferencesHelper.setUserPhone(phone)
//                AnyPref.put(user, "_CurrentUser")// 将私有token保存
//                //                    loginCyan(user);
//                //                    PushManager.getInstance().bindAlias(CustomApplication.getInstance(), user.getObjectId());// 绑定推送别名
//                finish()
//            },
//            { throwable ->
//                catLoadingView.dismissAllowingStateLoss()
//                CommonUtils.showToast("你输入的密码和账户名不匹配，请重新输入后重试")
//                Logger.e(throwable.getMessage())
//            })


//        val queryMap = HashMap<String, Any>()
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//            .queryUser(queryMap)
//            .map({ user -> Integer.parseInt(user.getCount()) > 0 }),
//            {
//                catLoadingView.show(supportFragmentManager, TAG)
//                val where = "[{\"key\":\"username\",\"value\":\"$phone\",\"operation\":\"=\",\"relation\":\"\"}]"
//                queryMap["where"] = where
//                queryMap["count"] = "1"
//                queryMap.put("limit", "0")
//            },
//            { isRegistered ->
//                catLoadingView.dismissAllowingStateLoss()
//                if (isRegistered) {
//                    CommonUtils.showToast("手机号已被注册，请尝试登录或通过忘记密码找回")
//                    finish()
//                } else {
//                    val intent = Intent()
//                    intent.setClass(mContext, RegisterNextActivity::class.java!!)
//                    intent.putExtra("phone", phone)
//                    startActivity(intent)
//                    finish()
//                }
//            },
//            { throwable ->
//                catLoadingView.dismissAllowingStateLoss()
//                CommonUtils.showToast(getString(R.string.register_error) + throwable.getMessage())
//                Logger.e(throwable.getMessage())
//            })

    private fun checkUserOk(username: String, password: String): Boolean {
        if (TextUtils.isEmpty(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_null)
            return false
        }
        if (!RegexUtils.isMobileExact(username)) {
            shakeAnimationAndFocusUi(et_username, R.string.toast_error_phone_error)
            return false
        }
        if (TextUtils.isEmpty(password)) {
            shakeAnimationAndFocusUi(et_password, R.string.toast_error_password_null)
            return false
        }
        if (!CommonUtils.isValidPassword(password)) {
            shakeAnimationAndFocusUi(et_password, R.string.toast_error_password_error)
            return false
        }
        return true
    }

    private fun shakeAnimationAndFocusUi(editTextView: ClearEditText, toastErrorMsgRes: Int) {
        ToastUtils.showShort(toastErrorMsgRes)
        editTextView.requestFocus()
        CommonUtils.setShakeAnimation(editTextView)
    }

    private fun registerSMSSDK() {
        // 注册短信回调
        SMSSDK.registerEventHandler(object : EventHandler() {
            override fun afterEvent(event: Int, result: Int, data: Any) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 回调完成
                    when (event) {
                        SMSSDK.EVENT_GET_VERIFICATION_CODE -> {
                            // 获取验证码成功
                            val smart = data as Boolean
                            if (smart) {
                                //通过Mob云验证
                            } else {
                                //依然走短信验证
                            }
                        }
                        SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE -> {
                            // 提交验证码成功
                        }
                        SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES -> {
                            // 返回支持发送验证码的国家列表
                        }
                    }
                    // TODO 利用国家代码和手机号码进行后续的操作
                    // 处理成功的结果
                    val phoneMap = data as HashMap<String, Any>
                    // 国家代码，如“86”
                    val country = phoneMap["country"] as String
                    // 手机号码，如“13800138000”
                    val phone = phoneMap["phone"] as String
                } else {
                    // TODO 处理错误的结果
                    (data as Throwable).printStackTrace()
                }
            }
        })
    }
}
