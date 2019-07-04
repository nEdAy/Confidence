package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.NineType
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.repository.NetworkState
import cn.neday.sheep.viewmodel.NineGoodsListViewModel
import kotlinx.android.synthetic.main.fragment_goods_list.*

/**
 * 9.9精选
 */
class NineGoodsListFragment(private val nineType: NineType) : BaseVMFragment<NineGoodsListViewModel>() {

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<NineGoodsListViewModel>? = NineGoodsListViewModel::class.java

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        mViewModel.showNineOpGoodsList(nineType.index.toString())
    }

    private fun initAdapter() {
        val mGoodsListAdapter = GoodsListAdapter {
            mViewModel.retry()
        }
        rv_goods.adapter = mGoodsListAdapter
        mViewModel.posts.observe(this, Observer<PagedList<Goods>> {
            mGoodsListAdapter.submitList(it)
        })
        mViewModel.networkState.observe(this, Observer {
            mGoodsListAdapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        mViewModel.refreshState.observe(this, Observer {
            srl_goods.isRefreshing = it == NetworkState.LOADING
        })
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { mViewModel.refresh() }
    }
}