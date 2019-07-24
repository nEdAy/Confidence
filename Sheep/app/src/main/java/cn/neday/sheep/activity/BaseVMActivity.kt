package cn.neday.sheep.activity

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.neday.sheep.viewmodel.BaseViewModel
import com.blankj.utilcode.util.ToastUtils

/**
 * Activity基类 + ViewModel
 *
 * @author nEdAy
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

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
        mViewModel.errMsg.observe(this, Observer {
            ToastUtils.showShort(it)
        })
    }

    abstract fun providerVMClass(): Class<VM>?
}
