package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import kotlinx.android.synthetic.main.fragment_main_rank.*
import java.util.*

/**
 * 超级分类
 * 1 -女装，2 -母婴，3 -美妆，4 -居家日用，5 -鞋品，6 -美食，7 -文娱车品，8 -数码家电，9 -男装，10 -内衣，11 -箱包，12 -配饰，13 -户外运动，14 -家装家纺
 */
class CategoryFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_rank

    override fun initView() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankItemFragment(1))
        mFragments.add(RankItemFragment(2))
        mFragments.add(RankItemFragment(3))
        tl_rank.setViewPager(vp_rank, resources.getStringArray(R.array.item_mall_array), activity, mFragments)
    }
}