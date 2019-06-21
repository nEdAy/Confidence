package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Fragment基类
 *
 * @author nEdAy
 */
abstract class BaseFragment : Fragment() {

    private var mRootView: View? = null

    abstract val layoutId: Int

    abstract fun setUpViews()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
    }
}
