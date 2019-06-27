package cn.neday.sheep.viewmodel

import androidx.lifecycle.MutableLiveData
import cn.neday.sheep.model.Banner
import cn.neday.sheep.network.repository.BannerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IndexViewModel : BaseViewModel() {

    val mBanners: MutableLiveData<List<Banner>> = MutableLiveData()

    private val repository by lazy { BannerRepository() }

    fun getBannerList() {
        launch {
            try {
                val response = withContext(Dispatchers.IO) { repository.getBannerList() }
                executeResponse(response, { mBanners.value = response.data }, { mErrMsg.value = response.msg })
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}