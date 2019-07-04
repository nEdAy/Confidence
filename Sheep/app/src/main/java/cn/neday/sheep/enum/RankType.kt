package cn.neday.sheep.enum

/**
 * 各大榜单
 * 1.实时销量榜
 * 2.全天销量榜
 * 3.热推榜
 */
enum class RankType(val index: Int) {
    SHISHIXIAOXIANGBANG(1),
    QUANTIANXIAOLIANGBANG(2),
    RETUIBANG(3),
}