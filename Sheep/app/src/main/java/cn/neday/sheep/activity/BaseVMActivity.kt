package cn.neday.sheep.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel

/**
 * Activity基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
