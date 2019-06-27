package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.IndexViewModel
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
        initViewPager()
        mViewModel.mBanners.observe(this, Observer {
            banner.setSource(it).startScroll()
            banner.setOnItemClickL { position ->
                AliTradeHelper(activity).showItemURLPage(it[position].clickURL)
            }
        })
    }

    private fun initHeader() {
        // init Search
        // ll_search.setOnClickListener { ActivityUtils.startActivity(SearchActivity::class.java) }
        // btn_search.setOnClickListener { ActivityUtils.startActivity(SearchActivity::class.java) }

        // init Banner
        mViewModel.getBannerList()

        // init Icon
        // ll_sign.setOnClickListener { ActivityUtils.startActivity(SignActivity::class.java) }
        // ll_shake.setOnClickListener { ActivityUtils.startActivity(ShakeActivity::class.java) }
        ll_shop.setOnClickListener { }
        ll_join.setOnClickListener { CommonUtils.joinQQGroup(activity) }
    }

    private fun initViewPager() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankFragment(RankType.SHI_SHI_XIAO_XIANG_BANG.index))
        mFragments.add(RankFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG.index))
        mFragments.add(RankFragment(RankType.RE_TUI_BANG.index))
        stl_index.setViewPager(vp_index, resources.getStringArray(R.array.item_mall_array), activity, mFragments)
    }

    override fun onResume() {
        super.onResume()
        banner.goOnScroll()
    }

    override fun onPause() {
        super.onPause()
        banner.pauseScroll()
    }

    /**
     * 各大榜单
     * 1.实时销量榜
     * 2.全天销量榜
     * 3.热推榜
     */
    enum class RankType(val index: Int) {
        SHI_SHI_XIAO_XIANG_BANG(1),
        QUAN_TIAN_XIAO_LIANG_BANG(2),
        RE_TUI_BANG(3),
    }
}
