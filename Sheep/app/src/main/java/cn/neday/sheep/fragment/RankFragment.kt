package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import kotlinx.android.synthetic.main.fragment_main_rank.*
import java.util.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_rank

    override fun setUpViews() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankItemFragment())
        mFragments.add(RankItemFragment())
        mFragments.add(RankItemFragment())
        tl_rank.setViewPager(vp_rank, resources.getStringArray(R.array.item_mall_array), activity, mFragments)
    }
}