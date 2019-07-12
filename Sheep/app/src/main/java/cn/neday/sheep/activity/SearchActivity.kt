package cn.neday.sheep.activity

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import cn.neday.sheep.R
import cn.neday.sheep.config.HawkConfig.HISTORYWORDS
import cn.neday.sheep.config.HawkConfig.HOTWORDS
import cn.neday.sheep.model.HistoryWords
import cn.neday.sheep.view.FlowLayout
import cn.neday.sheep.viewmodel.SearchViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ConvertUtils
import com.flyco.roundview.RoundTextView
import com.orhanobut.hawk.Hawk
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    override fun initView() {
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
        // historyWords
        val historyWords: TreeSet<HistoryWords>? = Hawk.get(HISTORYWORDS)
        fillKeyWordsAutoSpacingLayout(fl_search_history_words, historyWords?.map { it -> it.keyWords })
        // hotWords
        val hotWords: List<String>? = Hawk.get(HOTWORDS)
        fillKeyWordsAutoSpacingLayout(fl_search_hot_words, hotWords)
        mViewModel.getTop100()
        mViewModel.mHotWords.observe(this, Observer {
            fillKeyWordsAutoSpacingLayout(fl_search_hot_words, it.hotWords)
            Hawk.put(HOTWORDS, it.hotWords)
        })
    }

    private fun fillKeyWordsAutoSpacingLayout(flSearchKeyWords: FlowLayout, keyWords: List<String>?) {
        if (keyWords != null) {
            for (text in keyWords) {
                val textView = buildKeyWordsLabel(text)
                flSearchKeyWords.addView(textView)
            }
        }
    }

    private fun buildKeyWordsLabel(text: String): TextView {
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
            bundle.putString(SearchResultActivity.EXTRA, text)
            ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
        }
        return textView
    }
}