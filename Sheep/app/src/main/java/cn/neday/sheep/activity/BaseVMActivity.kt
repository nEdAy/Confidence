package cn.neday.sheep.activity

import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel

/**
 * Activity基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewModel: VM

    init {
        initProviderViewModel()
    }

    private fun initProviderViewModel() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    abstract fun providerVMClass(): Class<VM>?
}
