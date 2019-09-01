package com.dvm.appd.bosm.dbg.notification

import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.splash.views.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject
import java.lang.Exception

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage == null)
            return
        if (remoteMessage.data.size > 0) {
            try {
                var json = JSONObject(remoteMessage.data.toString())
                handleDataMessage(json)
            }catch (e: Exception) {
                // TODO setup firebase analytic log here
            }
        }

    }

    private fun handleDataMessage(json: JSONObject) {
        val title = json["title"]
        val body = json["body"]
        val channel = json["channel"]


    }

    private fun sendNotification(message: RemoteMessage?) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 , intent,
            PendingIntent.FLAG_ONE_SHOT)

        //TODO: Select appropriate notification channel
        // TODO: Change title and body of message appropriately
        val channelId = getString(R.string.chanel_id_general_notifications)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(message!!.notification!!.title)
            .setContentText(message.notification!!.body)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(java.lang.Integer.parseInt("0") , notificationBuilder)
        }
    }

}