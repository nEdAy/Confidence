package cn.neday.sheep.activity

import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.SearchResultViewModel
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search_result.*

class SearchResultActivity : BaseVMActivity<SearchResultViewModel>() {

    override val layoutId = R.layout.activity_search_result

    override fun providerVMClass(): Class<SearchResultViewModel>? = SearchResultViewModel::class.java

    override fun initView() {
        val keyWord = intent.extras?.get(extra) as String?
        keyWord?.let {
            titleBar_search_result.centerSearchEditText.setText(keyWord)
            mViewModel.getListSuperGoods(1, keyWord, 0, 0, "")
        }
        titleBar_search_result.setListener { _, action, text ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                mViewModel.getListSuperGoods(1, text, 0, 0, "")
            }
        }
        mViewModel.mGoods.observe(this, Observer {

        })
    }

    companion object {

        const val extra = "keyWord"
    }
}