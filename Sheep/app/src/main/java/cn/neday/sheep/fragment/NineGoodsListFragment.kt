package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.NineType
import cn.neday.sheep.viewmodel.NineGoodsListViewModel
import com.blankj.utilcode.util.LogUtils
import kotlinx.android.synthetic.main.fragment_goods_list.*

/**
 * 9.9精选
 */
class NineGoodsListFragment(private val nineType: NineType) : BaseVMFragment<NineGoodsListViewModel>() {

    private val mGoodsListAdapter: GoodsListAdapter = GoodsListAdapter()

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<NineGoodsListViewModel>? = NineGoodsListViewModel::class.java

    override fun initView() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { getNineOpGoodsList() }
        rv_goods.adapter = mGoodsListAdapter
        mViewModel.mGoods.observe(this, Observer {
            mGoodsListAdapter.submitList(it)
            srl_goods.isRefreshing = false
        })
        mViewModel.mErrMsg.observe(this, Observer {
            LogUtils.e(it)
            srl_goods.isRefreshing = false
        })
        getNineOpGoodsList()
    }

    private fun getNineOpGoodsList() {
        mViewModel.getNineOpGoodsList(nineType.index.toString())
    }
}