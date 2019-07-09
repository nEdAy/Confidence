package cn.neday.sheep.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.model.Goods


/**
 * Adapter for the [RecyclerView]
 *
 * @author nEdAy
 */
class GoodsListAdapter() :
    PagedListAdapter<Goods, GoodsViewHolder>(GOODS_DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            // val item = getItem(position)
            // (holder as GoodsViewHolder).update(item)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        return GoodsViewHolder.create(parent)
    }

    companion object {
        val GOODS_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Goods>() {
            override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean =
                oldItem.id == newItem.id
        }
    }
}