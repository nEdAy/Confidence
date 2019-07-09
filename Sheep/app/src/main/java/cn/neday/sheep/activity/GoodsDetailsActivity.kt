package cn.neday.sheep.activity

import android.net.Uri
import cn.neday.sheep.R
import cn.neday.sheep.model.Goods
import cn.neday.sheep.util.AliTradeHelper
import cn.neday.sheep.util.CommonUtils.convertPicUrlToUri
import cn.neday.sheep.view.ShareDialog
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_goods_details.*
import kotlinx.android.synthetic.main.include_item_action.*
import kotlinx.android.synthetic.main.include_port_item_details.*

/**
 * 商品详情页
 *
 * @author nEdAy
 */
class GoodsDetailsActivity : BaseActivity() {

    override val layoutId = R.layout.activity_goods_details

    override fun initView() {
        val goods = intent.extras?.get(extra) as Goods?
        if (goods == null) {
            ActivityUtils.finishActivity(this)
            return
        }
        titleBar_goods_details.centerTextView.text = goods.dtitle
        titleBar_goods_details.setListener { _, action, _ ->
            if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                ActivityUtils.finishActivity(this)
            } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                ShareDialog.newInstance(
                    "口袋快爆",
                    "您的好友向您推荐了一款商品",
                    goods.mainPic,
                    "http://www.neday.cn/index_.php?r=p/d&id=" + goods.id
                ).show(supportFragmentManager, "ShareDialog")
            }
        }
        ll_get.setOnClickListener {
            AliTradeHelper(this).showItemURLPage(goods.couponLink)
        }
        ll_buy.setOnClickListener {
            AliTradeHelper(this).showDetailPage(goods.goodsId)
        }
        ll_add.setOnClickListener { AliTradeHelper(this).showAddCartPage(goods.goodsId) }
        // 初始化商品主图
        Glide.with(this)
            .load(convertPicUrlToUri(goods.mainPic))
            .thumbnail(
                Glide.with(this)
                    .load(Uri.parse(goods.mainPic + "_100x100_jpg"))
            )
            .apply(
                RequestOptions().transform(RoundedCorners(10))
                    .placeholder(R.drawable.icon_stub)
                    .error(R.drawable.icon_error)
            )
            .into(iv_img_shower)
        // 显示标题
        tv_title.text = goods.title
        // 显示券后价
        tv_money.text = goods.actualPrice.toString()
        // 判断是否是天猫
        tv_mall_name.text = if (goods.shopType == 1) "天猫商城" else "淘宝"
        // 显示销量和评分
        tv_sales_num_and_dsr.text =
            "目前销量：" + goods.monthSales + " | 评分：" + goods.descScore
        // 显示介绍
        tv_introduce.text = goods.desc
        // 显示券详情信息
        tv_quan.text =
            "商品" + goods.originalPrice + "元在售，袋友们可领取" + goods.couponPrice + "元优惠券（剩余数量" + goods.couponReceiveNum + "/" + goods.couponTotalNum + "）" + "，实付" + goods.actualPrice + "元包邮到手，价格很不错，喜欢的袋友速速入手了！（有效期：" + goods.couponStartTime + " - " + goods.couponEndTime + "，使用条件：" + goods.couponConditions + "）"
    }


    companion object {

        const val extra = "goods"
    }
}