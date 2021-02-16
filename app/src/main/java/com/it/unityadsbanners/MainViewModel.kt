package com.it.unityadsbanners

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.IUnityBannerListener
import com.unity3d.services.banners.api.BannerListener

class MainViewModel : ViewModel() {


    val isShowBannerClicked = MutableLiveData(false)

}