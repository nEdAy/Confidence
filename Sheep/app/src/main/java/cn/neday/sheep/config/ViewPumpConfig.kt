package cn.neday.sheep.config

import cn.neday.sheep.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


object ViewPumpConfig {

    private const val FONT_PATH_GOTHAM = "font/gotham.ttf"
    private const val FONT_PATH_CALIBRI = "font/calibri.ttf"

    fun init() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(FONT_PATH_CALIBRI)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}
