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
import com.google.gson.JsonObject

class FirebaseMessagingService : FirebaseMessagingService() {

    lateinit var crashAnalytics: FirebaseAnalytics
    lateinit var roomDatabsae: AppDatabase

    override fun onCreate() {
        Log.d("Notification", "Service Started")
        crashAnalytics = FirebaseAnalytics.getInstance(this)
        roomDatabsae = AppModule(application).providesAppDatabase(application)
        super.onCreate()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d("Notification", "onMessageRecived called")
        if (remoteMessage == null) {
            Log.d("Notification", "Null Data")
        }

        Log.e("Notification", JsonObject().apply {
            addProperty("random_prop", "I'm a random property")
        }.toString())

        if (remoteMessage!!.data.size > 0) {
            Log.d("Notification", "Non null data")
            try {
                Log.e("Notification", remoteMessage.data.toString())
                var json = remoteMessage.data
                Log.d("Notification", "Recived json = ${remoteMessage.data.toString()}")
                handleDataMessage(json)
            }catch (e: Exception) {
                Log.e("Notification", "Failed to convert to json = $e")
                // TODO setup firebase analytic log here
            }
        }
        else {
            Log.d("Notification", "Entered Else ${remoteMessage.data.toString()}")
        }

    }

    @SuppressLint("CheckResult")
    private fun handleDataMessage(json: MutableMap<String, String>) {
        Log.d("Notification", "Handling data")
        val id = try {
            json["order_id"]
        } catch (e: Exception) {
            try {
                json["id"]
            } catch (e: Exception) {
                "0"
            }
        }
        val title = json["title"]
        val body = json["body"]
        val channel = try {
            json["channel"]
        } catch (e: Exception) {
            resources.getString(R.string.chanel_id_general_notifications)
        }

        val notificatoin = Notification(id = id!!, title = title!!, body = body!!, channel = channel!!)

        val sport = if(json.containsKey("sport")) {
            json["sport"]
        } else {
            null
        }
        val otp = if (json.containsKey("otp")) {
            json["otp"]
        } else {
            "0000"
        }
        Log.d("Notification", "Notification = ${notificatoin.toString()}")

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

        if (sport != null) {
            sendNotification(notificatoin, sport)
        } else if (otp != null) {
            sendNotification(notificatoin, notificatoin.id, otp)
        } else {
            sendNotification(notificatoin)
        }
    }

    private fun sendNotification(message: Notification) {
        Log.d("Notification", "Sending Notification")
        val pendingIntent = NavDeepLinkBuilder(applicationContext).setGraph(R.navigation.navigation_graph).setComponentName(MainActivity::class.java).setDestination(R.id.action_profile).createPendingIntent()

        //TODO: Select appropriate notification channel
        // TODO: Change icon appropriately
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, message.channel)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSound(defaultSoundUri)
            .build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(Integer.parseInt(message.id) , notificationBuilder)
        }
    }

    private fun sendNotification(message: Notification, sport: String) {

    }

    private fun sendNotification(message: Notification, orderId: String, otp: String) {
        if (otp.equals("0000")) {
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, message.channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(message.title)
                .setContentText(message.body)
                .setSound(defaultSoundUri)
                .build()
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(Integer.parseInt(orderId) , notificationBuilder)
            }
        } else {
            // TODO Correct the pending intent
            val pendingIntent = NavDeepLinkBuilder(applicationContext).setGraph(R.navigation.navigation_graph).setComponentName(MainActivity::class.java).setDestination(R.id.action_profile).createPendingIntent()
            val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationBuilder = NotificationCompat.Builder(this, message.channel)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(message.title)
                .setContentText(message.body)
                .setSound(defaultSoundUri)
                .addAction(NotificationCompat.Action(R.drawable.ic_action_event_24px, "View Otp", pendingIntent))
                .build()
            with(NotificationManagerCompat.from(this)) {
                // notificationId is a unique int for each notification that you must define
                notify(Integer.parseInt(orderId) , notificationBuilder)
            }
        }
    }
}