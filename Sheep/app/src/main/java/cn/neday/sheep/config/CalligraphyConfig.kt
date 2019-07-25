package cn.neday.sheep.config

import cn.neday.sheep.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

object CalligraphyConfig {

    private const val FONT_PATH_GOTHAM = "font/gotham.ttf"
    private const val FONT_PATH_CALIBRI = "font/calibri.ttf"

    fun init() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath(FONT_PATH_GOTHAM)
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }
}
