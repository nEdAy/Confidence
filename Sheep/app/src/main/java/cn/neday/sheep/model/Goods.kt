package cn.neday.sheep.model

data class Goods(
    // 商品id	Number	18926311
    val id: Int,
    // 淘宝商品id	 String	“589284195570”
    val goodsId: String,
    // 商品淘宝链接	String	“https://detail.tmall.com/item.htm?id=589284195570“
    val itemLink: String,
    // 淘宝标题	String	“西维里男士睡衣夏季韩版真丝短袖丝绸薄款宽松青年冰丝家居服套装”
    val title: String,
    // 大淘客短标题	String	“夏季睡衣男冰丝短袖丝绸家居服套装”
    val dtitle: String,
    // 商品原价	Number	38.5
    val originalPrice: Double,
    // 券后价	Number	28.5
    val actualPrice: Double,
    // 活动开始时间	String	“2019-03-29 10:00:06”
    val activityStartTime: String,
    // 活动结束时间	String	“2019-04-29 10:00:06”
    val activityEndTime: String,
    // 店铺类型，1-天猫，0-淘宝	Number	1
    val shopType: Int,
    // goldSellers	是否金牌卖家，1-金牌卖家，0-非金牌卖家	Number	0
    val goldSellers: Int,
    // 30天销量	Number	1030
    val monthSales: Int,
    // 2小时销量	Number	30
    val twoHoursSales: Int,
    // 当日销量	Number	20
    val dailySales: Int,
    // 佣金类型，0-通用，1-定向，2-高佣，3-营销计划	Number	3
    val commissionType: Int,
    // 推广文案	String	“宽松舒适，难以磨损典，雅而优美，倍感丝滑，质感优越，庄严而心仪，简约设计，色调清新脱俗，适合各种场合”
    val desc: String,
    // 领券量	Number	1000
    val couponReceiveNum: Int,
    // 优惠券链接	String	“https://uland.taobao.com/quan/detail?sellerId=4014489195&activityId=df6c5ba6aa6d4f21a303b50cca2f4a45“
    val couponLink: String,
    // 优惠券结束时间	String	“2019-04-08 23:59:59”
    val couponEndTime: String,
    // 优惠券开始时间	String	“2019-04-01 00:00:00”
    val couponStartTime: String,
    // 优惠券金额	Number	10
    val couponPrice: Double,
    // 优惠券使用条件	String	“38”
    val couponConditions: String,
    // 活动类型，1-无活动，2-淘抢购，3-聚划算	Number	1
    val activityType: Int,
    // 商品上架时间	String	“2019-03-29 10:00:06”
    val createTime: String,
    // 商品主图链接	String	“img.alicdn.com/imgextra/i2/4014489195/O1CN01kYlVPs2HnMKYwLLRm_!!4014489195.jpg”
    val mainPic: String,
    // 营销主图链接	String	“https://sr.ffquan.com/super_pic/o_1d6fpckjs1ii3h9dkibk9b7h38.jpg“
    val marketingMainPic: String,
    // 淘宝卖家id	String	“4014489195”
    val sellerId: String,
    // 商品在大淘客的分类id	Number	10
    val cid: Int,
    // 二级类目id	否	Number	大淘客的二级类目id，通过超级分类API获取。仅允许传一个二级id，当一级类目id和二级类目id同时传入时，会自动忽略二级类目id
    val scid: Int,
    // 商品在淘宝的分类id	Number	50012772
    val tbcid: Int,
    // 折扣力度	Number	0.74
    val discounts: Double,
    // 佣金比例	Number	20.01
    val commissionRate: Double,
    // 券总量	Number	7000
    val couponTotalNum: Int,
    // 是否海淘，1-海淘商品，0-非海淘商品	Number	0
    val haitao: Int,
    // 店铺名称	String	“西维里旗舰店”
    val shopName: String,
    // 淘宝店铺等级	Number	11
    val shopLevel: Int,
    // 描述分	Number	4.8
    val descScore: Double,
    // 描述相符	Number	4.8
    val dsrScore: Double,
    // 描述同行比	Number	0.0
    val dsrPercent: Double,
    // 服务态度	Number	4.8
    val shipScore: Double,
    // 服务同行比	Number	10.32
    val shipPercent: Double,
    // 物流服务	Number	4.8
    val serviceScore: Double,
    // 物流同行比	Number	5.82
    val servicePercent: Double,
    // 是否是品牌商品，0-不是品牌商品，1-是品牌商品	Number	1
    val brand: Int,
    // 品牌id	Number	2301323020
    val brandId: Long,
    // 品牌名称	String	“西维里”
    val brandName: String,
    // 热推值	Number	456
    val hotPush: Int,
    // 放单人名称	String	“啊甘团队”
    val teamName: String,
    // 是否天猫超市商品，1-天猫超市商品，0-非天猫超市商品	Number	0
    val tchaoshi: Int
)