package com.invite.kff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xwray.groupie.Item
import com.google.gson.GsonBuilder
import okhttp3.*
import org.json.JSONException
import java.io.IOException
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.GroupAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.video.view.*

class homeActivity : AppCompatActivity() {
    val adapters = GroupAdapter<ViewHolder>()
    lateinit var mAdView : AdView
    lateinit var toolbar: ActionBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-9225575939386535/3910152869"

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recpost.setLayoutManager(llm)
        recpost.setAdapter(adapters)
        val client = OkHttpClient()
        val url = "https://glamourat.com/videos.php"
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
                    gson.fromJson<AllUsers>(res, Array<AllUsers>::class.java) as Array<AllUsers>
                try {
                   runOnUiThread() {
                        try {
                            for (arr in allUserss) {
                                if(arr==null){
                                    // Toast.makeText(context, "تم تحميل منشورات", Toast.LENGTH_LONG).show()
                                    nomatch.setVisibility(View.VISIBLE)
                                    goallmatch.setOnClickListener {

                                    }
                                }else{
                                    adapters.add(MyIteam(arr))
                                }


                            }
                        } catch (e: JSONException) {
                            // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: JSONException) {
                    //Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show()
                }
            }
        })



    }

    data class AllUsers(
        var id_post: String,
        var title: String,
        var two: String,
        var img: String,
        var link: String,
        var date: String,
        var vue: String,
        var time: String

    )

    inner class MyIteam(var allUsers: AllUsers) : Item<ViewHolder>() {

        override fun getLayout(): Int {
            return R.layout.video
        }

        override fun bind(viewHolder: ViewHolder, position: Int) {
            val xml = viewHolder.itemView
           // xml.one.text=allUsers.one
           // xml.two.text=allUsers.two
            //xml.date.text=allUsers.date
             xml.vue.text="شاهده "+allUsers.vue
          //xml.vi1.text=allUsers.img
            Glide.with(this@homeActivity).load("https://i3.ytimg.com/vi/"+allUsers.img+"/hqdefault.jpg").into(xml.imageposter);
           // Glide.with(context!!).load("https://mebroukasport.com/libs/uplaod/"+allUsers.img2).into(xml.img2);
           // val wagrod = AnimationUtils.loadAnimation(context!!,R.anim.slidedown)
           //val snumber =xml.postlive
           // snumber.startAnimation(wagrod)

            xml.postv.setOnClickListener {

                val int= Intent(this@homeActivity,videoActivity::class.java)
                int.putExtra("link",allUsers.link)
                int.putExtra("id_post",allUsers.id_post)
                int.putExtra("img",allUsers.img)
                int.putExtra("time",allUsers.time)
                int.putExtra("vue",allUsers.vue)
               this@homeActivity.startActivity(int)
                Toast.makeText(this@homeActivity, "جاري تحميل القيديو انتظر ....", Toast.LENGTH_LONG).show()

            }
            xml.buttonss.setOnClickListener {

                val int= Intent(this@homeActivity,videoActivity::class.java)
                int.putExtra("link",allUsers.link)
                int.putExtra("id_post",allUsers.id_post)
                int.putExtra("img",allUsers.img)
                int.putExtra("time",allUsers.time)
                int.putExtra("vue",allUsers.vue)
                this@homeActivity.startActivity(int)
                Toast.makeText(this@homeActivity, "جاري تحميل القيديو انتظر ....", Toast.LENGTH_LONG).show()

            }

            val client = OkHttpClient()
            val urls = "https://glamourat.com/android/times.php?id="+allUsers.time+""
            val request = Request
                .Builder()
                .url(urls)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val res = response.body()!!.string()
                    val gson = GsonBuilder().create()
                    val timess =  gson.fromJson<TIME>(res, TIME::class.java)

                    xml.vi2.text=timess.success

                }
            })

        }
        private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->


            when (item.itemId) {
                R.id.home -> {
                    val int= Intent(this@homeActivity,homeActivity::class.java)
                    this@homeActivity.startActivity(int)
                    Log.d("home","home")

                    return@OnNavigationItemSelectedListener true
                }
                R.id.tv -> {
                  //  setFragment(liveFragment())
                    Toast.makeText(this@homeActivity, "خاقصية البث المياشر للبنات قريبا ....", Toast.LENGTH_LONG).show()
                    return@OnNavigationItemSelectedListener true
                }
               R.id.notif -> {

                return@OnNavigationItemSelectedListener true
            }

            }
            false
        }

    }
    data class TIME(var success:String)


}
