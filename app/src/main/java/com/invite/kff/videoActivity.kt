package com.invite.kff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import com.google.android.gms.ads.*
import com.google.gson.GsonBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.activity_video.*
import okhttp3.*
import java.io.IOException

class videoActivity : AppCompatActivity() {
    var id:Int?=null
    private lateinit var mInterstitialAd: InterstitialAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        ///ads inster
        MobileAds.initialize(this) {}
        val mNativeExpressAdView: NativeExpressAdView = findViewById(R.id.adView2)
        val requests = AdRequest.Builder().build()
        mNativeExpressAdView.loadAd(requests)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9225575939386535/8779336168"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                showInterstitialAd()
            }
        }

        ///fin ads iinterr
        val b: Bundle = intent.extras!!

        vue.setText(b.getString("vue"))

        third_party_player_view.getPlayerUiController().showFullscreenButton(true)
        third_party_player_view.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                val videoId = b.getString("link").toString()
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
       // window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        aaaa.setOnClickListener(View.OnClickListener {
                if (third_party_player_view.isFullScreen()) {
                    third_party_player_view.exitFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    // Show ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.hide()
                        fff.setVisibility(View.VISIBLE)
                    }
                } else {
                    third_party_player_view.enterFullScreen()
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
                    // Hide ActionBar
                    if (supportActionBar != null) {
                        supportActionBar!!.hide()
                        fff.setVisibility(View.GONE)
                    }
                }
            })

        vue.setText(b.getString("vue"))
        val client = OkHttpClient()
        val idmatch = b.getString("id_post")
        val url = "https://glamourat.com/android/addvue.php?id="+idmatch+""
        val request = Request
            .Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response) {
                val res = response.body()!!.string()
                val gson = GsonBuilder().create()
                val allUserss =
                    gson.fromJson<AllUsers>(res, AllUsers::class.java)

            }
        })
    }
    fun showInterstitialAd() {
        if (mInterstitialAd.isLoaded) {
            mInterstitialAd.show();
        }
    }

    data class AllUsers(var success:Boolean)
}
