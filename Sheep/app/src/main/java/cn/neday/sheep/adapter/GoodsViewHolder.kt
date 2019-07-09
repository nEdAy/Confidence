package cn.neday.sheep.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.activity.GoodsDetailsActivity
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils
import cn.neday.sheep.util.CommonUtils.convertPicUrlToUri
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.list_item_ranking.view.*
import java.util.concurrent.TimeUnit


class GoodsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(goods: Goods?) {
        if (goods == null) {
            return
        }
        itemView.run {
            tv_title.text = goods.dtitle
            tv_money.text = goods.actualPrice.toString()
            tv_sales_num.text = StringUtils.getString(R.string.tx_goods_monthSales, goods.monthSales)
            tx_get_value.text =
                StringUtils.getString(R.string.tx_goods_couponPrice, CommonUtils.getPrettyNumber(goods.couponPrice))
            tv_mall_name.text = StringUtils.getString(
                if (goods.shopType == 1) {
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
                .load(convertPicUrlToUri(goods.mainPic))
                .thumbnail(
                    Glide.with(this)
                        .load(Uri.parse(goods.mainPic + "_100x100.jpg"))
                )
                .apply(
                    RequestOptions().transform(RoundedCorners(10))
                        .placeholder(R.drawable.icon_stub)
                        .error(R.drawable.icon_error)
                )
                .into(iv_img_shower)
            setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable(GoodsDetailsActivity.extra, goods)
                ActivityUtils.startActivity(bundle, GoodsDetailsActivity::class.java)
            }
            setOnLongClickListener {
                AliTradeHelper((ActivityUtils.getActivityByView(this))).showAddCartPage(goods.goodsId)
                true
            }
            ll_get.setOnClickListener {
                AliTradeHelper(ActivityUtils.getActivityByView(this)).showItemURLPage(goods.couponLink)
                changePressedViewBg(it, R.drawable.bg_get_btn, R.drawable.bg_get_btn_pressed)
            }
            tx_buy_url.setOnClickListener {
                AliTradeHelper(ActivityUtils.getActivityByView(this)).showDetailPage(goods.goodsId)
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

        fun create(parent: ViewGroup): GoodsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_goods, parent, false)
            return GoodsViewHolder(view)
        }
    }
}
