package cn.neday.sheep.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import cn.neday.sheep.R
import cn.neday.sheep.model.Banner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flyco.banner.widget.Banner.BaseIndicatorBanner
import kotlinx.android.synthetic.main.adapter_banner.view.*

/**
 * 广告图片轮播
 *
 * @author nEdAy
 */
class AdvertisingBanner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    BaseIndicatorBanner<Banner, AdvertisingBanner>(context, attrs, defStyle) {

    override fun onCreateItemView(position: Int): View {
        val inflate = View.inflate(mContext, R.layout.adapter_banner, null)
        val (_, picURL) = mDatas[position]
        val itemWidth = mDisplayMetrics.widthPixels
        val itemHeight = (itemWidth * 0.417f).toInt()
        iv_banner.layoutParams = LinearLayout.LayoutParams(itemWidth, itemHeight)
        Glide.with(this)
            .load(Uri.parse(picURL))
            .apply(RequestOptions().circleCrop())
            .into(iv_banner)
        return inflate
    }
}
