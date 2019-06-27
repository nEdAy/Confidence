package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.RankAdapter
import cn.neday.sheep.viewmodel.RankViewModel
import kotlinx.android.synthetic.main.fragment_main_rank.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankFragment(private val rankType: Int) : BaseVMFragment<RankViewModel>() {

    private val mRankGoodsAdapter: RankAdapter = RankAdapter()

    override val layoutId: Int = R.layout.fragment_main_rank

    override fun providerVMClass(): Class<RankViewModel>? = RankViewModel::class.java

    override fun initView() {
        srl_rank_item.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_rank_item.setOnRefreshListener { getRankingList() }
        rv_rank_item_list.adapter = mRankGoodsAdapter
        mViewModel.mRankGoods.observe(this, Observer {
            mRankGoodsAdapter.submitList(it)
            srl_rank_item.isRefreshing = false
        })
        getRankingList()
    }

    private fun getRankingList() {
        mViewModel.getRankingList(rankType, null)
    }
}