package cn.neday.sheep.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.model.RankGoods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils.getPrettyNumber
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.list_item_ranking.view.*
import java.util.concurrent.TimeUnit

/**
 * Adapter for the [RecyclerView]
 *
 * @author nEdAy
 */
class RankingListAdapter : ListAdapter<RankGoods, RankingListAdapter.ViewHolder>(RankDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_ranking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(rankGoods: RankGoods) {
            itemView.run {
                tv_title.text = rankGoods.dtitle
                tv_money.text = rankGoods.actualPrice.toString()
                tv_sales_num.text = StringUtils.getString(R.string.tx_goods_monthSales, rankGoods.monthSales)
                tx_get_value.text =
                    StringUtils.getString(R.string.tx_goods_couponPrice, getPrettyNumber(rankGoods.couponPrice))
                tv_mall_name.text = StringUtils.getString(
                    if (rankGoods.istmall == 1) {
                        R.string.tx_tianmao
                    } else {
                        R.string.tx_taobao
                    }
                )
                lv_text.visibility = if (rankGoods.monthSales >= 10000) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
                Glide.with(this)
                    .load(convertPicUrlToUri(rankGoods.pic))
                    .apply(RequestOptions().circleCrop())
                    .into(iv_img_shower)
                setOnClickListener {
                    // ActivityUtils.startActivity(GoodsDetailsActivity::class.java)
                }
                setOnLongClickListener {
                    AliTradeHelper((ActivityUtils.getActivityByView(this))).showAddCartPage(rankGoods.goodsId.toString())
                    true
                }
                ll_get.setOnClickListener {
                    AliTradeHelper(ActivityUtils.getActivityByView(this)).showItemURLPage(rankGoods.couponLink)
                    changePressedViewBg(it, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
                }
                tx_buy_url.setOnClickListener {
                    if (rankGoods.commissionType == 1) { //todo: commissionType??
                        AliTradeHelper(ActivityUtils.getActivityByView(this)).showItemURLPage("http://www.neday.cn/index.php?r=p/d&id=" + rankGoods.id)
                    } else {
                        AliTradeHelper(ActivityUtils.getActivityByView(this)).showDetailPage(rankGoods.goodsId.toString())
                    }
                    changePressedViewBg(it, R.drawable.bg_buy_btn, R.drawable.bg_buy_btn_pressed)
                }
            }
        }

        /**
         * 点击时修改子控件背景样式并在0.5s后恢复
         *
         * @param view       要修改的子控件
         * @param bg         要恢复的背景
         * @param bgPressed 要变化的背景
         */
        @SuppressLint("CheckResult")
        private fun changePressedViewBg(view: View, bg: Int, bgPressed: Int) {
            view.isPressed = true
            view.setBackgroundResource(bgPressed)
            Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.isPressed = false
                    view.setBackgroundResource(bg)
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

private class RankDiffCallback : DiffUtil.ItemCallback<RankGoods>() {

    override fun areItemsTheSame(oldItem: RankGoods, newItem: RankGoods): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RankGoods, newItem: RankGoods): Boolean {
        return oldItem == newItem
    }
}