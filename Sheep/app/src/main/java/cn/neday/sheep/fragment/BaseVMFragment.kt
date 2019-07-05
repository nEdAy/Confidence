package cn.neday.sheep.fragment

import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel

/**
 * Fragment基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    protected lateinit var mViewModel: VM

    override fun prepareInitView() {
        super.prepareInitView()
        initProviderViewModel()
    }

    private fun initProviderViewModel() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null
}
