package cn.neday.sheep.activity

import cn.neday.sheep.R
import com.blankj.utilcode.util.ActivityUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    override val layoutId = R.layout.activity_search

    override fun initView() {
        titleBar_search.centerSearchEditText.hint = "请输入想搜索的商品关键词..."
        titleBar_search.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ActivityUtils.startActivity(SearchResultActivity::class.java)
            }
        }
    }
}