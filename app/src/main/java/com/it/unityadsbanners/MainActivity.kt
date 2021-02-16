package com.it.unityadsbanners

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerErrorInfo
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val testMode = true
    val enableLoad = true

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.isShowBannerClicked.observe(this) { showBanner ->
            if (showBanner) {
                loadBanner()
            }
        }

        UnityAds.initialize(this as Context, unityGameId, testMode, enableLoad)

        showBanner.apply {
            this.setOnClickListener {
                mainViewModel.isShowBannerClicked.value = true
            }
        }
    }

    private fun loadBanner() {
        showBanner.isEnabled = false
        // Create the top banner view object:
        val topBanner = BannerView(this@MainActivity, surfacingId, UnityBannerSize(320, 50))
        // Set the listener for banner lifecycle events:
        topBanner.listener = object : BannerView.IListener {

                override fun onBannerLoaded(p0: BannerView?) {
                    Log.e(TAG, "onBannerLoaded: $p0", )
                    p0?.apply {
                        this.removeAllViews()
                        alpha = 0.8f
                        layoutParams.width = RelativeLayout.LayoutParams.WRAP_CONTENT
                        setPadding(8,0,8,0)
                        setBackgroundColor(resources.getColor(R.color.teal_200, theme))
                        this.addView(TextView(this@MainActivity as Context).apply {
                            this.text = "I changed this banner"
                            setTextColor(resources.getColor(R.color.black, theme))
                            textSize = 30f
                        })

                        setOnClickListener {
                            onBannerClick(p0)
                        }

                    }
                }

                override fun onBannerClick(p0: BannerView?) {
                    Log.e(TAG, "onBannerClick: $p0", )
                    startActivity(Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(exampleUrl)
                    })
                    onBannerLeftApplication(p0)
                }

                override fun onBannerFailedToLoad(p0: BannerView?, p1: BannerErrorInfo?) {
                    Log.e(TAG, "onBannerFailedToLoad: $p0", )
                }

                override fun onBannerLeftApplication(p0: BannerView?) {
                    Log.e(TAG, "onBannerLeftApplication: $p0", )
                }

            }
        // Request a banner ad:
        topBanner.load()
        // add the banner view:
        mainViewGroup.apply {
            // Associate the banner view object with the parent view:
            addView(topBanner)
        }
    }

    companion object {
        const val exampleUrl = "https://www.impactplus.com/blog/awesome-website-homepage-video-examples"
        const val TAG = "MainActivityTAG"
        const val unityGameId = "1027971"
        const val surfacingId = "Banner"
    }

}