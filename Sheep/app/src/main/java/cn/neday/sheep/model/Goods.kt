package cn.neday.sheep.model

data class Goods(
    // 商品id，在大淘客的商品id Number 19259135
    var id: Int,
    // 淘宝商品id Number 590858626868
    var goodsId: Long,
    // 榜单名次 Number 1
    var ranking: Int,
    // 短标题 String 【李佳琦推荐】奢华芯肌素颜爆水霜
    var dtitle: String,
    // 券后价 Number 39.9
    var actualPrice: Double,
    // 佣金比例 Number 20.01
    var commissionRate: Double,
    // 优惠券金额 Number 300.1
    var couponPrice: Double,
    // 领券量 Number 4000
    var couponReceiveNum: Int,
    // 券总量 Number 100000
    var couponTotalNum: Int,
    // 月销量 Number 8824
    var monthSales: Int,
    // 2小时销量 Number 1138
    var twoHoursSales: Int,
    // 当天销量 Number 389
    var dailySales: Int,
    // 热推值 Number 42
    var hotPush: Int,
    // 商品主图 String https://img.alicdn.com/imgextra/i2/748376657/O1CN01LKI9Km1z2x8p5sGeN_!!748376657.jpg
    var pic: String,
    // 商品长标题 String ‘2019新款运动短裤女宽松防走光韩版外穿ins潮休闲学生bf夏季阔腿’
    var title: String,
    // 商品描述 String ‘多款可选！显瘦高腰韩版阔腿裤五分裤，不起球，不掉色。舒适面料，不挑身材，高腰设计’
    var description: String,
    // 商品原价 Number 22.9
    var originPrice: Double,
    // 优惠券链接 String ‘https://uland.taobao.com/quan/detail?sellerId=1687451966&activityId=ffef827d9a5747efbbe02a93c6d7ec13‘
    var couponLink: String,
    // 优惠券开始时间 String ‘2019-06-04 00:00:00’
    var couponStartTime: String,
    // 优惠券结束时间 String ‘2019-06-06 23:59:59’
    var couponEndTime: String,
    // 佣金类型 Number 3
    var commissionType: Int,
    // onSaleTime String ‘2019-06-03 17:55:18’
    var onSaleTime: String,
    // 活动类型 Number 1
    var activityType: Int,
    // 营销图 Array ‘https://img.alicdn.com/imgextra/i2/1687451966/O1CN01WNuZcl1QOTCM9NsrO_!!1687451966.jpg,https://img.alicdn.com/imgextra/i4/1687451966/O1CN01h2ih4v1QOTCOxlZDj_!!1687451966.jpg‘
    var picList: String,
    // 放单人名称 String 易折网
    var guideName: String,
    // 店铺类型 Number 1
    var istmall: Int,
    // 优惠券使用条件 Number 22.01
    var quanUsageCondition: Double
)
