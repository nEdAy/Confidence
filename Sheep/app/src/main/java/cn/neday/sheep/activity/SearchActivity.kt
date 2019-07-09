package cn.neday.sheep.activity

import android.graphics.Color
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.config.HawkConfig.HOTWORDS
import cn.neday.sheep.viewmodel.SearchViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ConvertUtils
import com.flyco.roundview.RoundTextView
import com.orhanobut.hawk.Hawk
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    override fun initView() {
        titleBar_search.centerSearchEditText.hint = "请输入想搜索的商品关键词..."
        titleBar_search.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ActivityUtils.startActivity(SearchResultActivity::class.java)
            }
        }
        val hotWords: List<String> = Hawk.get(HOTWORDS)
        fillAutoSpacingLayout(hotWords)
        mViewModel.mHotWords.observe(this, Observer {
            fillAutoSpacingLayout(hotWords)
            Hawk.put(HOTWORDS, it.hotWords)
        })
    }

    private fun fillAutoSpacingLayout(hotWords: List<String>) {
        for (text in hotWords) {
            val textView = buildLabel(text)
            fl_search_keyWords.addView(textView)
        }
    }

    private fun buildLabel(text: String): TextView {
        val textView = RoundTextView(this)
        textView.delegate.backgroundColor = Color.WHITE
        textView.delegate.backgroundPressColor = ContextCompat.getColor(this, R.color.red_deep)
        textView.delegate.cornerRadius = ConvertUtils.dp2px(10f)
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.setPadding(
            ConvertUtils.dp2px(16f),
            ConvertUtils.dp2px(8f),
            ConvertUtils.dp2px(16f),
            ConvertUtils.dp2px(8f)
        )
        return textView
    }
}