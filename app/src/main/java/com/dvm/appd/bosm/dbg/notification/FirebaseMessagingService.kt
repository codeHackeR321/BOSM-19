package com.dvm.appd.bosm.dbg.notification

import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dvm.appd.bosm.dbg.MainActivity
import com.dvm.appd.bosm.dbg.R
import com.dvm.appd.bosm.dbg.splash.views.SplashActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d("Notification" , "Message recived is ${p0!!.data}")
        sendNotification(p0)
        Toast.makeText(this, p0.data.toString(), Toast.LENGTH_LONG).show()
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
            .setContentTitle(message.notification!!.title)
            .setContentText(message.notification!!.body)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .build()
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(java.lang.Integer.parseInt("0") , notificationBuilder)
        }
    }

    override fun onNewToken(token: String?) {
        Log.d("FirebaseMgingService", "Refreshed token: $token")
        //
        // Toast.makeText(this, "New Token", Toast.LENGTH_LONG).show()
        // TODO send the new registration token to the server
    }

}