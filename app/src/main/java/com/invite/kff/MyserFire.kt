package com.invite.kff

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class MyserFire:FirebaseMessagingService() {

    private val TAG = this.javaClass.name

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    override fun onMessageReceived(msg: RemoteMessage?) {
        super.onMessageReceived(msg)

        if (msg != null) {
            if (msg.notification != null) {

                val m = msg.notification!!



                    val builder = NotificationCompat.Builder(this, "chat_app")
                    builder.setSmallIcon(R.drawable.ic_launcher_background)
                    builder.setContentTitle(m.title)
                    builder.setContentText(m.body)
                    builder.priority = NotificationCompat.PRIORITY_DEFAULT

                    val notification = builder.build()
                    val manager = NotificationManagerCompat.from(this)
                    val random = Random.nextInt(999999)
                    manager.notify(random, notification)


            }

        }

    }


}