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
import cn.neday.sheep.view.explosion_field.ExplosionField
import cn.neday.sheep.viewmodel.SearchViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ConvertUtils
import com.flyco.roundview.RoundTextView
import com.google.android.flexbox.FlexboxLayout
import com.orhanobut.hawk.Hawk
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_search.*
import java.util.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override val layoutId = R.layout.activity_search

    override fun providerVMClass(): Class<SearchViewModel>? = SearchViewModel::class.java

    private lateinit var mExplosionField: ExplosionField

    override fun initView() {
        initSearch()
        initHistoryWords()
        initHotWords()
        mViewModel.getTop100()
        mViewModel.mHotWords.observe(this, Observer {
            fillKeyWordsAutoSpacingLayout(fl_search_hot_words, it.hotWords)
            Hawk.put(HOTWORDS, it.hotWords)
        })
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
        mExplosionField = ExplosionField.attach2Window(this)
//        val historyWords: LiveData<TreeSet<HistoryWords>> = Hawk.get(HISTORYWORDS)
//        historyWords.observe(this, Observer {
//            fillKeyWordsAutoSpacingLayout(fl_search_history_words, it?.map { historyWords -> historyWords.keyWords })
//        })

        val historyWords: LinkedHashSet<String>? = Hawk.get(HISTORYWORDS)
        fillKeyWordsAutoSpacingLayout(fl_search_history_words, historyWords)
        tv_search_history_clean.setOnClickListener {
            mExplosionField.explode(fl_search_history_words, true)
            historyWords?.clear()
            Hawk.delete(HISTORYWORDS)
        }
    }

    private fun initHotWords() {
        val hotWords: List<String>? = Hawk.get(HOTWORDS)
        fillKeyWordsAutoSpacingLayout(fl_search_hot_words, hotWords)
    }

    private fun fillKeyWordsAutoSpacingLayout(flSearchKeyWords: FlexboxLayout, keyWords: Collection<String>?) {
        if (keyWords != null) {
            for (text in keyWords) {
                val textView = buildKeyWordsLabel(text)
                flSearchKeyWords.addView(textView)
            }
        } else {
            flSearchKeyWords.removeAllViews()
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
            mExplosionField.explode(it)
        }
        return textView
    }

    override fun onDestroy() {
        super.onDestroy()
        mExplosionField.clear()
    }
}