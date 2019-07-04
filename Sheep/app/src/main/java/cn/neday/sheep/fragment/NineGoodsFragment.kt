package cn.neday.sheep.fragment

import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import cn.neday.sheep.enum.NineType
import kotlinx.android.synthetic.main.fragment_main_nine_goods.*

/**
 * 9.9精选
 * -1-精选，1 -居家百货，2 -美食，3 -服饰，4 -配饰，5 -美妆，6 -内衣，7 -母婴，8 -箱包，9 -数码配件，10 -文娱车品
 */
class NineGoodsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_main_nine_goods

    override fun initView() {
        initViewPager()
    }

    private fun initViewPager() {
        val mFragments = ArrayList<Fragment>()
        mFragments.add(NineGoodsListFragment(NineType.JINGXUAN))
        mFragments.add(NineGoodsListFragment(NineType.JUJUABAIHUO))
        mFragments.add(NineGoodsListFragment(NineType.MEISHI))
        mFragments.add(NineGoodsListFragment(NineType.FUSHI))
        mFragments.add(NineGoodsListFragment(NineType.PEISHI))
        mFragments.add(NineGoodsListFragment(NineType.MEIZHUANG))
        mFragments.add(NineGoodsListFragment(NineType.NEIYI))
        mFragments.add(NineGoodsListFragment(NineType.MUYING))
        mFragments.add(NineGoodsListFragment(NineType.XIANGBAO))
        mFragments.add(NineGoodsListFragment(NineType.SHUMAPEIJIAN))
        mFragments.add(NineGoodsListFragment(NineType.WENYUCHEPIN))
        stl_nine_goods.setViewPager(
            vp_nine_goods,
            resources.getStringArray(R.array.nine_type_array),
            activity,
            mFragments
        )
    }
}