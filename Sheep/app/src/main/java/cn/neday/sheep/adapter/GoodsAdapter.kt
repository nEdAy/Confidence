package cn.neday.sheep.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.CommonUtils.getPrettyNumber
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.list_item_goods.view.*

/**
 * Adapter for the [RecyclerView] in [RankItemFragment].
 *
 * @author nEdAy
 */
class GoodsAdapter : ListAdapter<Goods, GoodsAdapter.ViewHolder>(GoodsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_goods, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(goods: Goods) {
            itemView.run {
                tv_title.text = goods.dtitle
                tv_money.text = goods.actualPrice.toString()
                tv_sales_num.text = StringUtils.getString(R.string.tx_goods_monthSales, goods.monthSales)
                tx_get_value.text = StringUtils.getString(R.string.tx_goods_couponPrice, getPrettyNumber(goods.couponPrice))
                tv_mall_name.text = StringUtils.getString(
                    if (goods.istmall == 1) {
                        R.string.tx_tianmao
                    } else {
                        R.string.tx_taobao
                    }
                )
                lv_text.visibility = if (goods.monthSales >= 10000) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                Glide.with(this)
                    .load(convertPicUrlToUri(goods.pic))
                    .apply(RequestOptions().circleCrop())
                    .into(iv_img_shower)
                setOnClickListener {
                    ActivityUtils.startActivity(GoodsDetailsActivity::class.java)
                }
            }
        }

        companion object {
            private fun convertPicUrlToUri(picUrl: String): Uri {
                return if (NetworkUtils.is4G()) {
                    Uri.parse(picUrl + StringUtils.getString(R.string._200x200_jpg))
                } else {
                    Uri.parse(picUrl + StringUtils.getString(R.string._300x300_jpg))
                }
            }
        }
    }
}

private class GoodsDiffCallback : DiffUtil.ItemCallback<Goods>() {

    override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem == newItem
    }
}