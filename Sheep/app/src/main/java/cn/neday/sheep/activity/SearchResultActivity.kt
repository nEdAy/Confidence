package cn.neday.sheep.activity

import androidx.lifecycle.Observer
import androidx.paging.PagedList
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.Goods
import cn.neday.sheep.network.NetworkState
import cn.neday.sheep.viewmodel.SearchResultViewModel
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseVMActivity<SearchResultViewModel>() {

    override val layoutId = R.layout.activity_search_result

    override fun providerVMClass(): Class<SearchResultViewModel>? = SearchResultViewModel::class.java

    override fun initView() {
        initSearchBar()
        initAdapter()
        initSwipeToRefresh()
    }

    private fun initSearchBar() {
        val keyWord = intent.extras?.get(extra) as String?
        keyWord?.let {
            titleBar_search_result.centerSearchEditText.setText(keyWord)
            mViewModel.getDtkSearchGoods(keyWord)
        }
        titleBar_search_result.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                mViewModel.getDtkSearchGoods(titleBar_search.searchKey)
            }
        }
    }

    private fun initAdapter() {
        val goodsListAdapter = GoodsListAdapter {
            mViewModel.retry()
        }
        rv_goods.adapter = goodsListAdapter
        mViewModel.posts.observe(this, Observer<PagedList<Goods>> {
            goodsListAdapter.submitList(it)
        })
        mViewModel.networkState.observe(this, Observer {
            goodsListAdapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh() {
        mViewModel.refreshState.observe(this, Observer {
            srl_goods.isRefreshing = it == NetworkState.LOADING
        })
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { mViewModel.refresh() }
    }

    companion object {

        const val extra = "keyWord"
    }
}