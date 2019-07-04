package cn.neday.sheep.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import cn.neday.sheep.R
import cn.neday.sheep.fragment.GoodsFragment
import cn.neday.sheep.fragment.IndexFragment
import cn.neday.sheep.fragment.NineGoodsFragment
import cn.neday.sheep.fragment.MeFragment
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.flyco.dialog.widget.ActionSheetDialog
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.tencent.bugly.beta.Beta
import java.util.*

/**
 * 主页
 *
 * @author nEdAy
 */
class MainActivity : BaseActivity() {

    private lateinit var mTabLayout: CommonTabLayout
    private val mFragments = ArrayList<Fragment>()
    private val mTabEntities = ArrayList<CustomTabEntity>()
    // 连续触发两次返回键则退出标记位
    private var mPressedBackTime: Long = 0
    // 记录当前Fragment的位置
    private var mCurrentTabIndex = 0

    override val layoutId = R.layout.activity_main

    override fun initView() {
        // 精选首页
        mFragments.add(IndexFragment())
        // 优惠快爆
        mFragments.add(GoodsFragment())
        // 好货专题
        mFragments.add(NineGoodsFragment())
        // 我的页面
        mFragments.add(MeFragment())
        val tabEntitiesArray = resources.getStringArray(R.array.tab_entities_array)
        val tabEntitiesLength = tabEntitiesArray.size
        for (index in 0 until tabEntitiesLength) {
            mTabEntities.add(TabEntity(tabEntitiesArray[index], iconSelectResIDs[index], iconUnSelectResIDs[index]))
        }
        mTabLayout = findViewById(R.id.tl_main_tab)
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_main_content, mFragments)
        // 恢复显示Fragment呈现位置
        setCurrentTab(mCurrentTabIndex)
    }

    /**
     * 根据onSaveInstanceState回调的状态，恢复当前Fragment state
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // 将这一行注释掉，阻止Activity没事瞎恢复Fragment的状态
        // super.onRestoreInstanceState(savedInstanceState);
        // 恢复记录的TabIndex
        mCurrentTabIndex = savedInstanceState.getInt("currentTabIndex")
    }

    /**
     * 根据onSaveInstanceState回调的状态，保存当前Fragment state
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // 记录当前的TabIndex
        outState.putInt("currentTabIndex", mCurrentTabIndex)
    }

    /**
     * 切换当前显示的页面
     *
     * @param currentTabIndex 页面序号
     */
    private fun setCurrentTab(currentTabIndex: Int) {
        // 记录当前position
        mCurrentTabIndex = currentTabIndex
        // 更改底部Tab按钮状态
        mTabLayout.currentTab = currentTabIndex
    }

    /**
     * 截获Back键动作 连续触发两次Back键则退出
     */
    override fun onBackPressed() {
        if (mPressedBackTime + SAFE_PRESSED_BACK_TIME > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            ToastUtils.showShort(getString(R.string.tx_pressed_back_hint))
        }
        mPressedBackTime = System.currentTimeMillis()
    }

    /**
     * 截获Menu键动作 弹出Menu菜单
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            showActionSheet()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun showActionSheet() {
        val menuItemArray = resources.getStringArray(R.array.menu_items_array)
        val dialog = ActionSheetDialog(this, menuItemArray, null)
        dialog.isTitleShow(false).layoutAnimation(null).show()
        dialog.setOnOperItemClickL { _, _, position, _ ->
            when (position) {
                0 -> Beta.checkUpgrade()
                1 -> ActivityUtils.startActivity(AboutActivity::class.java)
                2 -> ActivityUtils.finishAllActivities()
            }
            dialog.dismiss()
        }
    }

    internal class TabEntity(
        private val title: String,
        private val selectedIcon: Int,
        private val unSelectedIcon: Int
    ) : CustomTabEntity {

        override fun getTabTitle(): String {
            return title
        }

        override fun getTabSelectedIcon(): Int {
            return selectedIcon
        }

        override fun getTabUnselectedIcon(): Int {
            return unSelectedIcon
        }
    }

    companion object {
        private const val SAFE_PRESSED_BACK_TIME = 2000
        private val iconSelectResIDs = intArrayOf(
            R.drawable.tab_index_select,
            R.drawable.tab_item_select,
            R.drawable.tab_library_select,
            R.drawable.tab_me_select
        )
        private val iconUnSelectResIDs = intArrayOf(
            R.drawable.tab_index_unselect,
            R.drawable.tab_item_unselect,
            R.drawable.tab_library_unselect,
            R.drawable.tab_me_unselect
        )
    }
}