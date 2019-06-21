package cn.neday.sheep.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel

/**
 * Fragment基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initProviderViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initProviderViewModel() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null
}
