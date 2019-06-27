package cn.neday.sheep.activity

import android.text.TextUtils
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.ClearEditText
import cn.neday.sheep.viewmodel.LoginViewModel
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

    override fun initView() {
        initTitleAndBackgroundByTime()
        initEditViewByLastUsername()
        btn_login.setOnClickListener { login() }
        btn_register.setOnClickListener { register() }
        tv_lostPassword.setOnClickListener { resetPassword() }
        mViewModel.mUser.observe(this, Observer {
            ActivityUtils.finishActivity(this)
        })
    }

    private fun initEditViewByLastUsername() {
        val username = Hawk.get<String>("username")
        if (!TextUtils.isEmpty(username)) {
            et_username.setText(username)
            et_password.isFocusable = true
            et_password.isFocusableInTouchMode = true
            et_password.requestFocus()
            et_password.requestFocusFromTouch()
        }
    }

    private fun initTitleAndBackgroundByTime() {
        val isAM = TimeUtils.getValueByCalendarField(TimeUtils.getNowDate(), Calendar.AM_PM) == Calendar.AM
        if (isAM) {
            iv_login_bg.setImageResource(R.drawable.good_morning_img)
            tv_login_title.text = getString(R.string.tx_login_title_AM)
        } else {
            iv_login_bg.setImageResource(R.drawable.good_night_img)
            tv_login_title.text = getString(R.string.tx_login_title_PM)
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
                // mViewModel.register(username, password)
            } else {
                ToastUtils.showShort(R.string.network_tips)
            }
        }
    }

    private fun resetPassword() {

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
}
