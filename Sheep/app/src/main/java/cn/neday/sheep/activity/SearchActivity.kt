package cn.neday.sheep.activity

import android.graphics.Color
import android.os.Bundle
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
        titleBar_search.centerSearchEditText.hint = getString(R.string.tx_search_hint)
        titleBar_search.setListener { _, action, text ->
            if (action == CommonTitleBar.ACTION_SEARCH_SUBMIT || action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                val bundle = Bundle()
                bundle.putString(SearchResultActivity.extra, text)
                ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
            }
        }
        val hotWords: List<String>? = Hawk.get(HOTWORDS)
        fillHotWordAutoSpacingLayout(hotWords)
        mViewModel.getTop100()
        mViewModel.mHotWords.observe(this, Observer {
            fillHotWordAutoSpacingLayout(hotWords)
            Hawk.put(HOTWORDS, it.hotWords)
        })
    }

    private fun fillHotWordAutoSpacingLayout(hotWords: List<String>?) {
        if (hotWords != null) {
            for (text in hotWords) {
                val textView = buildHotWordLabel(text)
                fl_search_keyWords.addView(textView)
            }
        }
    }

    private fun buildHotWordLabel(text: String): TextView {
        val textView = RoundTextView(this)
        textView.delegate.run {
            backgroundColor = Color.WHITE
            backgroundPressColor = ContextCompat.getColor(mContext, R.color.red)
            cornerRadius = ConvertUtils.dp2px(4f)
            strokeColor = Color.GRAY
            strokePressColor = ContextCompat.getColor(mContext, R.color.red)
            strokeWidth = ConvertUtils.dp2px(0.3f)
            textPressColor = Color.WHITE
        }
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        textView.setTextColor(ContextCompat.getColor(this, R.color.textColor))
        textView.setPadding(
            ConvertUtils.dp2px(8f),
            ConvertUtils.dp2px(4f),
            ConvertUtils.dp2px(8f),
            ConvertUtils.dp2px(4f)
        )
        textView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(SearchResultActivity.extra, text)
            ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
        }
        return textView
    }
}