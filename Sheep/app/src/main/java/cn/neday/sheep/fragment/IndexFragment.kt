package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import kotlinx.android.synthetic.main.fragment_main_rank.*
import java.util.ArrayList

/**
 * 首页
 */
class IndexFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_rank

    override fun initView() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankItemFragment(1))
        mFragments.add(RankItemFragment(2))
        mFragments.add(RankItemFragment(3))
        tl_rank.setViewPager(vp_rank, resources.getStringArray(R.array.item_mall_array), activity, mFragments)
    }
}
