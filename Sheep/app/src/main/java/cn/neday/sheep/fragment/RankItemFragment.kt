package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
class RankItemFragment : BaseFragment() {

    private val mGoodsAdapter: GoodsAdapter = GoodsAdapter()

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(RankItemViewModel::class.java)
    }

    override val layoutId: Int = R.layout.fragment_main_rank_item

    override fun setUpViews() {
        lifecycle.addObserver(viewModel)
        srl_rank_item.setOnRefreshListener { viewModel.getRankingList(1, null) }
        rv_rank_item_list.adapter = mGoodsAdapter
        // 将数据的变化反映到UI上
        viewModel.mRankGoods.observe(this, Observer { mGoodsAdapter.submitList(it) })
        viewModel.getRankingList(1, null)
    }
}