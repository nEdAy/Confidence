package cn.neday.sheep.activity

import cn.neday.sheep.R
import cn.neday.sheep.view.GuideBanner
import com.blankj.utilcode.util.ActivityUtils
import com.orhanobut.hawk.Hawk
import java.util.*

/**
 * 导航页
 *
 * @author nEdAy
 */
class GuideActivity : BaseActivity() {

    private lateinit var mGuideBanner: GuideBanner

    override val layoutId = R.layout.activity_guide

    override fun initView() {
        mGuideBanner = findViewById(R.id.guide_banner)
        mGuideBanner.setIndicatorWidth(6f)
            .setIndicatorHeight(6f)
            .setIndicatorGap(12f)
            .setIndicatorCornerRadius(3.5f)
            .setBarColor(R.color.gray)
            .barPadding(0f, 10f, 0f, 10f)
            .setSource(getGuides())
            .startScroll()
        mGuideBanner.setOnGuideJumpClick {
            Hawk.put("isFirstStartApp", false)
            ActivityUtils.startActivity(MainActivity::class.java)
            ActivityUtils.finishActivity(this)
        }
    }

    private fun getGuides(): ArrayList<Int> {
        val list = ArrayList<Int>()
        list.add(R.drawable.guide_img_1)
        list.add(R.drawable.guide_img_2)
        list.add(R.drawable.guide_img_3)
        return list
    }

    override fun onResume() {
        super.onResume()
        mGuideBanner.goOnScroll()
    }

    override fun onPause() {
        super.onPause()
        mGuideBanner.pauseScroll()
    }
}
