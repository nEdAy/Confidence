package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import cn.neday.sheep.enum.RankType
import kotlinx.android.synthetic.main.fragment_main_goods.*

class GoodsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_goods

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankingListFragment(RankType.SHISHIXIAOXIANGBANG))
        mFragments.add(RankingListFragment(RankType.QUANTIANXIAOLIANGBANG))
        mFragments.add(RankingListFragment(RankType.RETUIBANG))
        stl_index.setViewPager(vp_index, resources.getStringArray(R.array.rank_type_array), activity, mFragments)
        vp_index.offscreenPageLimit = 2
        // https://blog.csdn.net/maosidiaoxian/article/details/78051601
        vp_index.postDelayed({ vp_index.requestApplyInsets() }, 500)
    }
}