package cn.neday.sheep.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.activity.SearchResultActivity
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.list_item_key_words.view.*

class KeyWordsListAdapter : ListAdapter<String, KeyWordsListAdapter.ViewHolder>(KEY_WORDS_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_key_words, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(keyWords: String) {
            itemView.rtv_keyWords.text = keyWords
            itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(SearchResultActivity.EXTRA, keyWords)
                ActivityUtils.startActivity(bundle, SearchResultActivity::class.java)
            }
        }
    }

    companion object {
        val KEY_WORDS_DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}
