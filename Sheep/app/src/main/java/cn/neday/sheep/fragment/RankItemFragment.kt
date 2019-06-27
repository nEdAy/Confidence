package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.GoodsAdapter
import cn.neday.sheep.viewmodel.RankItemViewModel
import kotlinx.android.synthetic.main.fragment_main_rank_item.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankItemFragment(private val rankType: Int) : BaseVMFragment<RankItemViewModel>() {

    private val mGoodsAdapter: GoodsAdapter = GoodsAdapter()

    override val layoutId: Int = R.layout.fragment_main_rank_item

    override fun providerVMClass(): Class<RankItemViewModel>? = RankItemViewModel::class.java

    override fun initView() {
        srl_rank_item.setOnRefreshListener { getRankingList() }
        rv_rank_item_list.adapter = mGoodsAdapter
        // 将数据的变化反映到UI上
        mViewModel.mRankGoods.observe(this, Observer {
            mGoodsAdapter.submitList(it)
            srl_rank_item.isRefreshing = false
        })
        getRankingList()
    }

    private fun getRankingList() {
        mViewModel.getRankingList(rankType, null)
    }
}