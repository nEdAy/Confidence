package cn.neday.sheep.fragment

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.RankingListAdapter
import cn.neday.sheep.enum.RankType
import cn.neday.sheep.viewmodel.RankingListViewModel
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_goods_list.*

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
class RankingListFragment(private val rankType: RankType) : BaseVMFragment<RankingListViewModel>() {

    private val mRankingListAdapter: RankingListAdapter = RankingListAdapter()

    override val layoutId: Int = R.layout.fragment_goods_list

    override fun providerVMClass(): Class<RankingListViewModel>? = RankingListViewModel::class.java

    override fun initView() {
        initAdapter()
        initSwipeToRefresh()
        getRankingList()
    }

    private fun initAdapter() {
        rv_goods.adapter = mRankingListAdapter
        mViewModel.mRankGoods.observe(this, Observer {
            mRankingListAdapter.submitList(it)
            srl_goods.isRefreshing = false
        })
        mViewModel.mErrMsg.observe(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    private fun initSwipeToRefresh() {
        srl_goods.setColorSchemeResources(R.color.red, R.color.orange, R.color.green, R.color.blue)
        srl_goods.setOnRefreshListener { getRankingList() }
    }

    private fun getRankingList() {
        mViewModel.getRankingList(rankType.index, "")
    }
}