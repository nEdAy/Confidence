package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.enum.NineType
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.NetworkState
import cn.neday.sheep.network.Status
import cn.neday.sheep.viewmodel.NineGoodsListViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_goods_list.*

/**
 * 9.9精选
 */
class NineGoodsListFragment(private val nineType: NineType) : BaseVMFragment<NineGoodsListViewModel>() {

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<NineGoodsListViewModel>? = NineGoodsListViewModel::class.java

    override fun initView() {
        initStateView()
        initAdapter()
        initSwipeToRefresh()
        mViewModel.showNineOpGoodsList(nineType.index.toString())
    }

    private fun initStateView() {
        stateView.setOnRetryClickListener { mViewModel.retry() }
        mViewModel.networkState.observe(this, Observer {
            stateView.showEmpty()
            if (it.status == Status.RUNNING) {
                stateView.showLoading()
            }
            if (it.status == Status.SUCCESS) {
                stateView.showContent()
            }
            if (it.status == Status.FAILED) {
                stateView.showRetry()
            }
            if (it.msg != null) {
                ToastUtils.showShort(it.msg)
            }
        })
    }

    private fun initAdapter() {
        val mGoodsListAdapter = GoodsListAdapter()
        rv_goods.adapter = GoodsListAdapter()
        mViewModel.posts.observe(this, Observer<PagedList<Goods>> {
            mGoodsListAdapter.submitList(it)
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