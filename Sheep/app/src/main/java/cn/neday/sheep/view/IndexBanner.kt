package cn.neday.sheep.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import cn.neday.sheep.model.Banner
import com.bumptech.glide.Glide
import com.flyco.banner.widget.Banner.BaseIndicatorBanner


/**
 * 广告图片轮播
 *
 * @author nEdAy
 */
class IndexBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    BaseIndicatorBanner<Banner, IndexBanner>(context, attrs, defStyle) {

    override fun onCreateItemView(position: Int): View {
        val inflate = View.inflate(mContext, cn.neday.sheep.R.layout.adapter_banner, null)
        val picURL = mDatas[position].picURL
        val itemWidth = mDisplayMetrics.widthPixels
        val itemHeight = (itemWidth * 0.417f).toInt()
        val ivBanner = inflate.findViewById<ImageView>(cn.neday.sheep.R.id.iv_banner)
        ivBanner.layoutParams = LinearLayout.LayoutParams(itemWidth, itemHeight)
        Glide.with(this)
            .load(Uri.parse(picURL))
            .override(itemWidth, itemHeight)
            .centerCrop()
            .into(ivBanner)
        return inflate
    }
}
