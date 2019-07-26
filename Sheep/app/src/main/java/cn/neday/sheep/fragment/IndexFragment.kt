package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.activity.LoginActivity
import cn.neday.sheep.activity.SearchActivity
import cn.neday.sheep.activity.ShakeActivity
import cn.neday.sheep.activity.SignInActivity
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.IndexViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_main_index.*
import kotlinx.android.synthetic.main.include_main_index_header.*
import kotlinx.android.synthetic.main.include_main_index_icon.*


/**
 * 首页
 */
class IndexFragment : BaseVMFragment<IndexViewModel>() {

    override val layoutId: Int = R.layout.fragment_main_index

    override fun providerVMClass(): Class<IndexViewModel>? = IndexViewModel::class.java

    override fun initView() {
        initHeader()
//        initViewPager()
        mViewModel.banners.observe(this, Observer {
            banner.setSource(it).startScroll()
            banner.setOnItemClickL { position ->
                AliTradeHelper(activity).showItemURLPage(it[position].url)
            }
        })
    }

    private fun initHeader() {
        // Search
        titleBar_index.centerSearchEditText.hint = getString(R.string.tx_search_hint)
        titleBar_index.centerSearchEditText.setOnClickListener { ActivityUtils.startActivity(SearchActivity::class.java) }
        titleBar_index.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ActivityUtils.startActivity(SearchActivity::class.java)
            }
        }
        // Icon
        ll_sign.setOnClickListener { ActivityUtils.startActivity(SignInActivity::class.java) }
        ll_shake.setOnClickListener { ActivityUtils.startActivity(ShakeActivity::class.java) }
        ll_shop.setOnClickListener { ActivityUtils.startActivity(LoginActivity::class.java) }
        ll_join.setOnClickListener { CommonUtils.joinQQGroup(activity) }
    }

//    private fun initViewPager() {
//        val mFragments = ArrayList<Fragment>()
//        mFragments.add(RankingListFragment(RankType.SHI_SHI_XIAO_XIANG_BANG))
//        mFragments.add(RankingListFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG))
//        mFragments.add(RankingListFragment(RankType.RE_TUI_BANG))
//        stl_ranking.setViewPager(vp_ranking, resources.getStringArray(R.array.ranking_type_array), activity, mFragments)
//        vp_ranking.offscreenPageLimit = 2
//        // https://blog.csdn.net/maosidiaoxian/article/details/78051601
//        vp_ranking.postDelayed({ vp_ranking.requestApplyInsets() }, 500)
//    }

    override fun onStart() {
        super.onStart()
        mViewModel.getBannerList()
    }

    override fun onResume() {
        super.onResume()
        banner.goOnScroll()
    }

    override fun onPause() {
        super.onPause()
        banner.pauseScroll()
    }
}
