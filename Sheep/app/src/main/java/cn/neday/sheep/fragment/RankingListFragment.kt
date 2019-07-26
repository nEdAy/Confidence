package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.adapter.RankingListAdapter
import cn.neday.sheep.enum.RankType
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.viewmodel.RankingListViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.fragment_goods_list.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankingListFragment(private val rankType: RankType) : BaseVMFragment<RankingListViewModel>() {

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<RankingListViewModel>? = RankingListViewModel::class.java

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        loadInitial()
    }

    private fun initAdapter() {
        val rankingListAdapter = RankingListAdapter()
        rankingListAdapter.bindToRecyclerView(rv_goods)
        val emptyView = layoutInflater.inflate(R.layout.include_no_data, rv_goods.parent as ViewGroup, false)
        emptyView.setOnClickListener { loadInitial() }
        rankingListAdapter.emptyView = emptyView
        rankingListAdapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            val bundle = Bundle()
            bundle.putSerializable(GoodsDetailsActivity.extra, goods)
            ActivityUtils.startActivity(bundle, GoodsDetailsActivity::class.java)
        }
        rankingListAdapter.onItemLongClickListener = BaseQuickAdapter.OnItemLongClickListener { adapter, _, position ->
            val goods = adapter.getItem(position) as Goods
            AliTradeHelper(activity).showAddCartPage(goods.goodsId)
            true
        }
        rankingListAdapter.onItemChildClickListener =
            BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                val goods = adapter.getItem(position) as Goods
                when (view.id) {
                    R.id.ll_get -> {
                        AliTradeHelper(activity).showItemURLPage(goods.couponLink)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                    }
                    R.id.tx_buy_url -> {
                        AliTradeHelper(activity).showDetailPage(goods.goodsId)
                        CommonUtils.changePressedViewBg(view, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                    }
                }
            }
        rv_goods.adapter = rankingListAdapter
        mViewModel.rankGoods.observe(this, Observer {
            rankingListAdapter.setNewData(it)
            srl_goods.isRefreshing = false
        })
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
            srl_goods.isRefreshing = false
            rankingListAdapter.loadMoreFail()
        })
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { loadInitial() }
    }

    private fun loadInitial() {
        mViewModel.getRankingList(rankType.index)
    }
}