package cn.neday.sheep.model

/**
 * 广告
 */
data class Banner(
    // ID 主键
    val id: Int?,
    // 标题
    val title: String?,
    // 連接
    var clickURL: String?,
    // 圖片URL
    var picURL: String?,
    // 状态
    var state: Boolean?
)
