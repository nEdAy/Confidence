package cn.neday.sheep.enum

/**
 * 9.9精选
 * -1-精选
 * 1 -居家百货
 * 2 -美食
 * 3 -服饰
 * 4 -配饰
 * 5 -美妆
 * 6 -内衣
 * 7 -母婴
 * 8 -箱包
 * 9 -数码配件
 * 10 -文娱车品
 */
enum class NineType(val index: Int) {
    JINGXUAN(-1),
    JUJUABAIHUO(1),
    MEISHI(2),
    FUSHI(3),
    PEISHI(4),
    MEIZHUANG(5),
    NEIYI(6),
    MUYING(7),
    XIANGBAO(8),
    SHUMAPEIJIAN(9),
    WENYUCHEPIN(10)
}