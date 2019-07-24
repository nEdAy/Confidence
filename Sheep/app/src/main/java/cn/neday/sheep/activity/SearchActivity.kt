package cn.neday.sheep.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.adapter.KeyWordsListAdapter
import cn.neday.sheep.view.explosion_field.ExplosionField
import cn.neday.sheep.viewmodel.SearchViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    private lateinit var mExplosionField: ExplosionField

    override fun initView() {
        initSearch()
        initHistoryWords()
        initHotWords()
        mViewModel.getHotWords()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getHistoryWords()
    }

    private fun initSearch() {
        titleBar_search.centerSearchEditText.hint = getString(R.string.tx_search_hint)
        titleBar_search.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                val bundle = Bundle()
                bundle.putString(SearchResultActivity.EXTRA, titleBar_search.searchKey)
                ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
            } else if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            }
        }
    }

    private fun initHistoryWords() {
        val historyWordsListAdapter = KeyWordsListAdapter()
        rv_search_history_words.adapter = historyWordsListAdapter
        rv_search_history_words.layoutManager = getFlexboxLayoutManager()
        mViewModel.mHistoryWords.observe(this, Observer {
            it?.run { historyWordsListAdapter.submitList(it.reversed()) }
        })

        mExplosionField = ExplosionField.attach2Window(this)
        tv_search_history_clean.setOnClickListener {
            mExplosionField.explode(rv_search_history_words)
            mViewModel.cleanHistoryWords()
        }
    }

    private fun initHotWords() {
        val hotWordsListAdapter = KeyWordsListAdapter()
        rv_search_hot_words.adapter = hotWordsListAdapter
        rv_search_hot_words.layoutManager = getFlexboxLayoutManager()
        mViewModel.mHotWords.observe(this, Observer {
            it?.hotWords?.run { hotWordsListAdapter.submitList(it.hotWords.reversed()) }
        })
    }

    private fun getFlexboxLayoutManager(): FlexboxLayoutManager {
        val flexboxLayoutManager = FlexboxLayoutManager(this)
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        return flexboxLayoutManager
    }

    override fun onDestroy() {
        super.onDestroy()
        mExplosionField.clear()
    }
}