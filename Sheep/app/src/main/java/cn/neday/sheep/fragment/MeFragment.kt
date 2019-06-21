package cn.neday.sheep.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import cn.neday.sheep.R
import cn.neday.sheep.activity.AboutActivity
import cn.neday.sheep.model.User
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.view.DampView
import cn.neday.sheep.view.RiseNumberTextView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.flyco.animation.BounceEnter.BounceTopEnter
import com.flyco.animation.SlideExit.SlideBottomExit
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.orhanobut.hawk.Hawk

/**
 * 我的
 */
class MeFragment : BaseFragment(), DampView.IRefreshListener {

    private lateinit var mParentView: View
    private lateinit var mCreditsValue: RiseNumberTextView
    private lateinit var mCurrentUser: User
    private lateinit var mAvatar: ImageView
    private lateinit var mNickname: TextView
    private lateinit var iv_damp: ImageView
    private lateinit var rl_top: RelativeLayout
    private lateinit var rl_me: RelativeLayout
    private lateinit var tv_l_and_r: TextView
    private lateinit var rl_credits: RelativeLayout
    private lateinit var iv_vip: ImageView

    override val layoutId: Int = R.layout.fragment_main_me

    override fun setUpViews() {
//        iv_vip = mParentView.findViewById(R.id.iv_vip)
//        rl_top = mParentView.findViewById(R.id.rl_top)
//        rl_me = mParentView.findViewById(R.id.rl_me)
//        rl_credits = mParentView.findViewById(R.id.rl_credits)
//        tv_l_and_r = mParentView.findViewById(R.id.tv_l_and_r)
//        iv_damp = mParentView.findViewById(R.id.iv_damp)
//        mAvatar = mParentView.findViewById(R.id.iv_me_avatar)
//        mNickname = mParentView.findViewById(R.id.tv_nickname)
//        mCreditsValue = mParentView.findViewById(R.id.tv_credits_value)
//        val dampView = mParentView.findViewById<DampView>(R.id.dampView)
//        dampView.setImageView(iv_damp)
//        dampView.setOnRefreshListener(this)
////        mParentView.findViewById<View>(R.id.ll_option)
////            .setOnClickListener { ActivityUtils.startActivity(AccountActivity::class.java) }
////        mParentView.findViewById<View>(R.id.iv_level)
////            .setOnClickListener { ActivityUtils.startActivity(VipActivity::class.java) }
////        mParentView.findViewById<View>(R.id.rl_me)
////            .setOnClickListener { ActivityUtils.startActivity(AccountActivity::class.java) }
//        mParentView.findViewById<View>(R.id.ll_encourage).setOnClickListener { encourageWe() }
//        mParentView.findViewById<View>(R.id.ll_about)
//            .setOnClickListener { ActivityUtils.startActivity(AboutActivity::class.java) }
//        mParentView.findViewById<View>(R.id.ll_feedback).setOnClickListener { CommonUtils.joinQQGroup(activity) }
//        mParentView.findViewById<View>(R.id.ll_attention).setOnClickListener { attentionWe() }
//        mParentView.findViewById<View>(R.id.ll_share).setOnClickListener {
//            //            ShareDialog(activity).builder(
////                getString(R.string.app_name), "口袋快爆-每天千款优惠券秒杀，一折限时疯抢！",
////                "http://app-10046956.cos.myqcloud.com/toAvatar.png",
////                "http://a.app.qq.com/o/simple.jsp?pkgname=com.neday.bomb"
////            ).show()
//        }
//        mParentView.findViewById<View>(R.id.rl_credits).setOnClickListener {
//            val intent = Intent()
//            intent.putExtra("userId", mCurrentUser.id)
////            ActivityUtils.startActivity(CreditsHistoryActivity::class.java, intent)
//        }
////        tv_l_and_r.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }
////        mParentView.findViewById<View>(R.id.rl_0).setOnClickListener { aliTradeUtils.showCart() }
////        mParentView.findViewById<View>(R.id.rl_1).setOnClickListener { aliTradeUtils.showOrder(1) }
////        mParentView.findViewById<View>(R.id.rl_2).setOnClickListener { aliTradeUtils.showOrder(2) }
////        mParentView.findViewById<View>(R.id.rl_3).setOnClickListener { aliTradeUtils.showOrder(3) }
////        mParentView.findViewById<View>(R.id.rl_4).setOnClickListener { aliTradeUtils.showOrder(4) }
    }


    override fun onResume() {
        super.onResume()
        initUserInfoAndChangeSkin()
    }


    override fun onRefresh() {
//        if (NetworkUtils.isAvailable()) {
//            getUserById(mCurrentUser.id)
//        }
    }

    /**
     * 更新用户信息、点击状态、换皮肤
     */
    private fun initUserInfoAndChangeSkin() {
        // mCurrentUser = User.getCurrentUser()
//        val isNetworkAvailable = NetworkUtils.isAvailable()
//        if (NetworkUtils.isAvailable()) {
//            getUserById(mCurrentUser.id)
//            rl_top.visibility = View.VISIBLE
//            rl_me.visibility = View.VISIBLE
//            rl_credits.visibility = View.VISIBLE
//            tv_l_and_r.visibility = View.GONE
//        } else {
//            rl_top.visibility = View.GONE
//            rl_me.visibility = View.GONE
//            rl_credits.visibility = View.GONE
//            tv_l_and_r.visibility = View.VISIBLE
//        }
//        val selfCenterBgIndex = Hawk.get("self_center_bg_index", 0)
//        iv_damp.setImageResource(selfCenterBgResIDs[selfCenterBgIndex])
    }

    /**
     * 获取用户信息
     *
     * @param id 用户ID
     */
    private fun getUserById(id: Long?) {
//        toSubscribe(RxFactory.getUserServiceInstance(null)
//            .getUser(id),
//            { user ->
//                mCreditsValue.withNumber(user.getCredit())
//                mCreditsValue.start()
//                refreshUser(user)
//            },
//            { throwable -> LogUtils.e(throwable.getMessage()) })
    }

    /**
     * 更新用户信息前端显示
     *
     * @param user 用户信息
     */
    private fun refreshUser(user: User) {
        refreshAvatar(user.avatarURL)
        val nickname = user.nickname
        if (TextUtils.isEmpty(nickname) || nickname == getString(R.string.default_nickname)) {
            mNickname.text = getString(R.string.default_nickname)
//            mNickname.setOnClickListener { ActivityUtils.startActivity(UpdateInfoActivity::class.java) }
        } else {
            mNickname.text = nickname
        }
        val credit = user.credit
        when {
            credit >= 200000 -> iv_vip.setImageResource(R.drawable.level_10)
            credit >= 100000 -> iv_vip.setImageResource(R.drawable.level_9)
            credit >= 50000 -> iv_vip.setImageResource(R.drawable.level_8)
            credit >= 15000 -> iv_vip.setImageResource(R.drawable.level_7)
            credit >= 5000 -> iv_vip.setImageResource(R.drawable.level_6)
            credit >= 2000 -> iv_vip.setImageResource(R.drawable.level_5)
            credit >= 1000 -> iv_vip.setImageResource(R.drawable.level_4)
            credit >= 500 -> iv_vip.setImageResource(R.drawable.level_3)
            credit >= 200 -> iv_vip.setImageResource(R.drawable.level_2)
            credit >= 100 -> iv_vip.setImageResource(R.drawable.level_1)
            else -> iv_vip.setImageResource(R.drawable.level_0)
        }
    }

    /**
     * 更新头像 refreshAvatar
     */
    private fun refreshAvatar(avatarUrl: String?) {
        if (avatarUrl != null && avatarUrl != "") {
            val avatarUri = Uri.parse(avatarUrl)
            Glide.with(this)
                .load(avatarUri)
                .into(mAvatar)
        } else {
            mAvatar.setImageResource(R.drawable.avatar_default)
        }
    }

    /**
     * 关注微信
     */
    private fun attentionWe() {
        val dialog = NormalDialog(activity)
        dialog.content("跳转微信—通讯录-添加朋友-查找公众号—搜索\"神马快爆订阅号\"(点击跳转微信可以直接粘贴公众号哦)")
            .style(NormalDialog.STYLE_TWO)
            .btnNum(3)
            .btnText(getString(R.string.tx_cancel), getString(R.string.tx_determine), "跳转微信")
            .showAnim(BounceTopEnter())
            .dismissAnim(SlideBottomExit())
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                // 复制数据到剪切板
                val clipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", getString(R.string.app_name))
                clipboardManager.primaryClip = clipData
                try {
                    val intent = activity?.packageManager?.getLaunchIntentForPackage("com.tencent.mm")
                    if (intent != null) {
                        ActivityUtils.startActivity(intent)
                    }
                } catch (ignored: Exception) {
                    ToastUtils.showLong("您尚未安装微信APP")
                }
                dialog.superDismiss()
            })
    }

    /**
     * 鼓励我们----打开应用商店
     */
    private fun encourageWe() {
        val dialog = NormalDialog(activity)
        dialog.content("袋王亲，如果您觉得我们做的还不错，请给我一些鼓励吧！")
            .style(NormalDialog.STYLE_TWO)
            .btnNum(3)
            .btnText(getString(R.string.tx_cancel), getString(R.string.tx_determine), "跳转商店")
            .showAnim(BounceTopEnter())
            .dismissAnim(SlideBottomExit())
            .show()
        dialog.setOnBtnClickL(
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL { dialog.dismiss() },
            OnBtnClickL {
                CommonUtils.launchAppDetail(activity, null)
                dialog.superDismiss()
            }
        )
    }

    companion object {
        private val selfCenterBgResIDs = intArrayOf(
            R.drawable.selfcenter_bg_0,
            R.drawable.selfcenter_bg_1,
            R.drawable.selfcenter_bg_2,
            R.drawable.selfcenter_bg_3,
            R.drawable.selfcenter_bg_4
        )
    }
}
