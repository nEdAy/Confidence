package cn.neday.sheep.activity

import cn.neday.sheep.R
import cn.neday.sheep.model.Goods

/**
 * 商品详情页
 *
 * @author nEdAy
 */
class GoodsDetailsActivity : BaseActivity() {
    override val layoutId = R.layout.activity_goods_details

    private var goods: Goods? = null

    private fun getExtra(): String {
        return "goods"
    }

    override fun initView() {
//        // 获取商品信息
//        goods = intent.getParcelableExtra(getExtra())
//        if (goods == null) {
//            ActivityUtils.finishActivity(this)
//            return
//        }
//        //注册点击事件监听
//        ll_get.setOnClickListener {
//            AliTradeHelper(this).showItemURLPage(goods.couponLink) }
//        ll_buy.setOnClickListener {
//        AliTradeHelper(this).showDetailPage(goods.goodsId) }
//        ll_add.setOnClickListener { AliTradeHelper(this).showAddCartPage(goods.goodsId) }
//        //初始化标题栏
////        val img_url = goods.getPic()
////        val cms_url = "http://www.neday.cn/index_.php?r=p/d&id=" + goods.getID()
////        initTopBarForBoth("精选详情", getString(R.string.tx_back), "分享") {
////            ShareDialog(mContext).builder(
////                "口袋快爆",
////                "您的好友向您推荐了一款商品",
////                img_url,
////                cms_url
////            ).show()
////        }
//        //初始化商品主图
//        val uri = Uri.parse(img_url)
//        if (NetworkUtils.is4G()) {
//            if (sharedPreferencesHelper.isAllowProvinceFlowModel()) {
//                iv_img_shower.setImageURI(uri.toString() + getString(R.string._120x120_jpg))
//            } else {
//                Glide.with(this)
//                    .load(GoodsViewHolder.convertPicUrlToUri(goods.mainPic))
//                    .thumbnail(
//                        Glide.with(this)
//                            .load(Uri.parse(goods.mainPic + "_100x100_jpg"))
//                    )
//                    .apply(
//                        RequestOptions().transform(RoundedCorners(10))
//                            .placeholder(R.drawable.icon_stub)
//                            .error(R.drawable.icon_error)
//                    )
//                    .into(iv_img_shower)
//            }
//        } else {
//            iv_img_shower.setImageURI(uri.toString() + getString(R.string._300x300_jpg))
//        }
//        // 显示标题
//        tv_title.setText(goods.title)
//        // 显示券后价
//        val price = goods.actualPrice
//        tv_money.setText(price)
//        // 判断是否是天猫
//        val mall_name = if (goods.getIsTmall() === 1) "天猫商城" else "淘宝"
//        tv_mall_name.text = mall_name
//        // 显示销量和评分
//        tv_sales_num_and_dsr.text =
//            "目前销量：" + goods.getSales_num() + " | 评分：" + goods.getDsr()
//        // 显示介绍
//        tv_introduce.setText(goods.getIntroduce())
//        // 显示券详情信息
//        tv_quan.text =
//            (mall_name + goods.getOrg_Price() + "元在售，袋友们可领取" + goods.getQuan_price()
//                    + "元优惠券（剩余数量" + goods.getQuan_surplus() + "/"
//                    + (goods.getQuan_surplus() + goods.getQuan_receive()) + "）"
//                    + "，实付" + price + "元包邮到手，价格很不错，喜欢的袋友速速入手了！（有效期："
//                    + goods.getQuan_time() + "，使用条件：" + goods.getQuan_condition() + "）")
    }
}
