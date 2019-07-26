package cn.neday.sheep.adapter

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import cn.neday.sheep.R
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.CommonUtils
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 * Adapter for the [RecyclerView]
 *
 * @author nEdAy
 */
class GoodsListAdapter : BaseQuickAdapter<Goods, BaseViewHolder>(R.layout.list_item_goods, null) {

    override fun convert(helper: BaseViewHolder, goods: Goods) {
        helper.setText(R.id.tv_title, goods.dtitle)
            .setText(R.id.tv_money, goods.actualPrice.toString())
            .setText(R.id.tv_sales_num, StringUtils.getString(R.string.tx_goods_monthSales, goods.monthSales))
            .setText(
                R.id.tx_get_value,
                StringUtils.getString(R.string.tx_goods_couponPrice, CommonUtils.getPrettyNumber(goods.couponPrice))
            )
            .setText(
                R.id.tv_mall_name, StringUtils.getString(
                    if (goods.shopType == 1) {
                        R.string.tx_tianmao
                    } else {
                        R.string.tx_taobao
                    }
                )
            )
            .setGone(R.id.lv_text, goods.monthSales >= 20000)
            .addOnClickListener(R.id.ll_get, R.id.tx_buy_url)

        Glide.with(mContext)
            .load(CommonUtils.convertPicUrlToUri(goods.mainPic))
            .thumbnail(
                Glide.with(mContext)
                    .load(Uri.parse(goods.mainPic + "_100x100.jpg"))
            )
            .apply(
                RequestOptions().transform(RoundedCorners(10))
                    .placeholder(R.drawable.icon_stub)
                    .error(R.drawable.icon_error)
            )
            .into(helper.getView(R.id.iv_img_shower))
    }
}