package cn.neday.sheep.model

data class RankGoods(
    // 商品id，在大淘客的商品id Number 19259135
    val id: Int,
    // 淘宝商品id Number 590858626868
    val goodsId: Long,
    // 榜单名次 Number 1
    val ranking: Int,
    // 短标题 String 【李佳琦推荐】奢华芯肌素颜爆水霜
    val dtitle: String,
    // 券后价 Number 39.9
    val actualPrice: Double,
    // 佣金比例 Number 20.01
    val commissionRate: Double,
    // 优惠券金额 Number 300.1
    val couponPrice: Double,
    // 领券量 Number 4000
    val couponReceiveNum: Int,
    // 券总量 Number 100000
    val couponTotalNum: Int,
    // 月销量 Number 8824
    val monthSales: Int,
    // 2小时销量 Number 1138
    val twoHoursSales: Int,
    // 当天销量 Number 389
    val dailySales: Int,
    // 热推值 Number 42
    val hotPush: Int,
    // 商品主图 String https://img.alicdn.com/imgextra/i2/748376657/O1CN01LKI9Km1z2x8p5sGeN_!!748376657.jpg
    val pic: String,
    // 商品长标题 String ‘2019新款运动短裤女宽松防走光韩版外穿ins潮休闲学生bf夏季阔腿’
    val title: String,
    // 商品描述 String ‘多款可选！显瘦高腰韩版阔腿裤五分裤，不起球，不掉色。舒适面料，不挑身材，高腰设计’
    val description: String,
    // 商品原价 Number 22.9
    val originPrice: Double,
    // 优惠券链接 String ‘https://uland.taobao.com/quan/detail?sellerId=1687451966&activityId=ffef827d9a5747efbbe02a93c6d7ec13‘
    val couponLink: String,
    // 优惠券开始时间 String ‘2019-06-04 00:00:00’
    val couponStartTime: String,
    // 优惠券结束时间 String ‘2019-06-06 23:59:59’
    val couponEndTime: String,
    // 佣金类型 Number 3
    val commissionType: Int,
    // onSaleTime String ‘2019-06-03 17:55:18’
    val onSaleTime: String,
    // 活动类型 Number 1
    val activityType: Int,
    // 营销图 Array ‘https://img.alicdn.com/imgextra/i2/1687451966/O1CN01WNuZcl1QOTCM9NsrO_!!1687451966.jpg,https://img.alicdn.com/imgextra/i4/1687451966/O1CN01h2ih4v1QOTCOxlZDj_!!1687451966.jpg‘
    val picList: String,
    // 放单人名称 String 易折网
    val guideName: String,
    // 店铺类型 Number 1
    val istmall: Int,
    // 优惠券使用条件 Number 22.01
    val quanUsageCondition: Double
)
