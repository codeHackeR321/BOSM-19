package com.dvm.appd.bosm.dbg.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.NavDeepLinkBuilder
import androidx.room.Room
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.di.AppModule
import com.dvm.appd.bosm.dbg.shared.AppDatabase
import com.dvm.appd.bosm.dbg.splash.views.SplashActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import java.lang.Exception
import android.os.Bundle

class FirebaseMessagingService : FirebaseMessagingService() {

    lateinit var crashAnalytics: FirebaseAnalytics

    override fun onCreate() {
        Log.d("Notification", "Service Started")
        crashAnalytics = FirebaseAnalytics.getInstance(this)
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("Notification", "onMessageRecived called")
        if (remoteMessage == null)
            return
        if (remoteMessage.data.size > 0) {
            Log.d("Notification", "Non null data")
            try {
                var json = JSONObject(remoteMessage.data.toString())
                Log.d("Notification", "Recived json = ${remoteMessage.data.toString()}")
                handleDataMessage(json)
            }catch (e: Exception) {
                Log.e("Notification", "Failed to convert to json = $e")
                // TODO setup firebase analytic log here
            }
        }

    }

    @SuppressLint("CheckResult")
    private fun handleDataMessage(json: JSONObject) {
        Log.d("Notification", "Handling data")
        val id = json.getString("id")
        val title = json.getString("title")
        val body = json.getString("body")
        val channel = json.getString("channel")
        val notificatoin = Notification(id = id, title = title, body = body, channel = channel)
        Log.d("Notification", "Notification = ${notificatoin.toString()}")
        sendNotification(notificatoin)

        var roomDatabsae = AppModule(application).providesAppDatabase(application)
        Log.d("Notification", "Room instance = ${roomDatabsae.toString()}")
        roomDatabsae.notificationDao().insertNotification(notificatoin).doOnSubscribe {
            Log.d("Notification", "Started adding data to table")

        }
            .subscribe({
                Log.d("Notification", "Succesfully added data to table")
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, notificatoin.id)
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, notificatoin.title)
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Book")
                // TODO setup firbase analytic log
            },
                {
                    Log.e("Notification", "Error adding notification to room \n${it}")
                    // TODO setup firbase analytic log
                })
    }

    private fun sendNotification(message: Notification) {
        Log.d("Notification", "Sending Notification")
        val pendingIntent = NavDeepLinkBuilder(applicationContext).setGraph(com.dvm.appd.bosm.dbg.R.navigation.navigation_graph).setComponentName(MainActivity::class.java).setDestination(
            com.dvm.appd.bosm.dbg.R.id.action_profile).createPendingIntent()

        //TODO: Select appropriate notification channel
        // TODO: Change title and body of message appropriately
        val channelId = getString(com.dvm.appd.bosm.dbg.R.string.chanel_id_general_notifications)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, message.channel)
            .setSmallIcon(com.dvm.appd.bosm.dbg.R.drawable.ic_launcher_background)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSound(defaultSoundUri)
            .build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(java.lang.Integer.parseInt(message.id) , notificationBuilder)
        }
    }

}