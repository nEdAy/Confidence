package cn.neday.sheep.model

/**
 * 广告
 */
data class Banner(
    // 标题
    val title: String?,
    // 点击链接
    var url: String?,
    // 图片链接
    var picture: String?,
    // 状态
    var state: Boolean?
) : BaseModel()
