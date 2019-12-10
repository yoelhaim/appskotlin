package com.invite.kff

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic("glamour")
        val wagrod = AnimationUtils.loadAnimation(this,R.anim.slidedown)
        val wagrou = AnimationUtils.loadAnimation(this,R.anim.slideup)
        val img = AnimationUtils.loadAnimation(this,R.anim.imgrot)
        val img800 = AnimationUtils.loadAnimation(this,R.anim.img800)
        val img1000 = AnimationUtils.loadAnimation(this,R.anim.img1000)


        val snumber = findViewById(R.id.imageView3) as ImageView
        // val groupe = findViewById(R.id.groupe) as ImageView
        // val glamour = findViewById(R.id.glamour) as ImageView
        val wagro = findViewById(R.id.yyyy) as TextView



        snumber.startAnimation(img)
        //  glamour.startAnimation(img1000)
        //   groupe.startAnimation(img800)
        wagro.startAnimation(wagrod)
        //  wagro.startAnimation(wagrou)
        val time = object : CountDownTimer(2000, 1000) {

            override fun onFinish() {

                val intent = Intent(this@MainActivity, homeActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTick(p0: Long) {}
        }
        time.start()
    }
}
