package cn.neday.sheep.fragment

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

    override fun initView() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(RankItemFragment(RankType.SHI_SHI_XIAO_XIANG_BANG.index))
        mFragments.add(RankItemFragment(RankType.QUAN_TIAN_XIAO_LIANG_BANG.index))
        mFragments.add(RankItemFragment(RankType.RE_TUI_BANG.index))
        tl_rank.setViewPager(vp_rank, resources.getStringArray(R.array.item_mall_array), activity, mFragments)
    }

    enum class RankType(val index: Int) {
        SHI_SHI_XIAO_XIANG_BANG(1),
        QUAN_TIAN_XIAO_LIANG_BANG(2),
        RE_TUI_BANG(3),
    }
}