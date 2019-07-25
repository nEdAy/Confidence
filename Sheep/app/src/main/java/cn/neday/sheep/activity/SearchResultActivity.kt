package cn.neday.sheep.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsListAdapter
import cn.neday.sheep.model.Goods
import cn.neday.sheep.model.Pages
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.SearchResultViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.include_no_data.view.*


class SearchResultActivity : BaseVMActivity<SearchResultViewModel>() {

    override val layoutId = R.layout.activity_search_result

    override fun providerVMClass(): Class<SearchResultViewModel>? = SearchResultViewModel::class.java

    override fun initView() {
        val keyWord = intent.extras?.get(EXTRA) as String?
        keyWord?.let {
            initSearchBar(keyWord)
            initAdapter(keyWord)
            initSwipeToRefresh(keyWord)
        } ?: ActivityUtils.finishActivity(this)
    }

    private fun initSearchBar(keyWord: String) {
        titleBar_search_result.centerSearchEditText.setText(keyWord)
        titleBar_search_result.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                mViewModel.getDtkSearchGoods(titleBar_search.searchKey)
            } else if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }
    }

    private fun initAdapter(keyWord: String) {
        val goodsListAdapter = GoodsListAdapter()
        rv_goods.adapter = goodsListAdapter
        goodsListAdapter.bindToRecyclerView(rv_goods)
        val emptyView = layoutInflater.inflate(R.layout.include_no_data, rv_goods.parent as ViewGroup, false)
        emptyView.tv_noData.setOnClickListener {
            mViewModel.getDtkSearchGoods(keyWord)
        }
        goodsListAdapter.emptyView = emptyView
        goodsListAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            val bundle = Bundle()
            bundle.putSerializable(GoodsDetailsActivity.extra, goods)
            ActivityUtils.startActivity(bundle, GoodsDetailsActivity::class.java)
        }
        goodsListAdapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            AliTradeHelper(this).showAddCartPage(goods.goodsId)
            true
        }
        goodsListAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val goods = adapter.getItem(position) as Goods
                when (view.id) {
                    R.id.ll_get -> {
                        AliTradeHelper(this).showItemURLPage(goods.couponLink)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        AliTradeHelper(this).showDetailPage(goods.goodsId)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
        goodsListAdapter.setPreLoadNumber(PREFETCH_DISTANCE)
        goodsListAdapter.setOnLoadMoreListener({
            mViewModel.getDtkSearchGoods(keyWord, mViewModel.mCurrentPageId)
        }, rv_goods)
        rv_goods.adapter = goodsListAdapter
        mViewModel.pageGoods.observe(this, Observer<Pages<Goods>> {
            if (goodsListAdapter.itemCount >= it.totalNum) {
                goodsListAdapter.loadMoreEnd()
            } else {
                setAdapterData(goodsListAdapter, it)
                mViewModel.mCurrentPageId = it.pageId
                goodsListAdapter.loadMoreComplete()
            }
        })

        mViewModel.errMsg.observe(this, Observer {
            srl_goods.isRefreshing = false
            goodsListAdapter.loadMoreFail()
        })
    }

    private fun setAdapterData(adapter: GoodsListAdapter, data: Pages<Goods>) {
        if (mViewModel.mCurrentPageId == SearchResultViewModel.LOAD_INITIAL_PAGE_ID) {
            srl_goods.isRefreshing = false
            adapter.setNewData(data.list)
        } else {
            adapter.addData(data.list)
        }
    }

    private fun initSwipeToRefresh(keyWord: String) {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener {
            mViewModel.getDtkSearchGoods(keyWord)
        }
    }

    companion object {

        const val EXTRA = "keyWord"
        private const val PREFETCH_DISTANCE = 20
    }
}