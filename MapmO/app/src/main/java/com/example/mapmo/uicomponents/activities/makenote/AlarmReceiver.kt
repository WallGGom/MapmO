package com.example.mapmo.uicomponents.activities.makenote

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.mapmo.R
import com.example.mapmo.uicomponents.activities.landing.MainActivity
import java.util.*

class AlarmReceiver: BroadcastReceiver() {
    var notificationId: Int = 0
    companion object {
        const val TAG = "AlarmReceiver"
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("MakeNoteActivity", "Receiver: " + Date().toString())
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager
        if (intent != null) {
            notificationId = intent.getIntExtra("Id", 0)
        }
        Log.d("onReceive", "$notificationId")
        createNotificationChannel(notificationId.toString())
        deliverNotification(context, notificationId)
    }

    private fun deliverNotification(context: Context, num: Int) {
        val contentIntent = Intent(context, MainActivity::class.java)

        val contentPendingIntent = PendingIntent.getActivity(
            context,
            num,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Alert")
                .setContentText("This is alarm")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(num, builder.build())
    }

    fun createNotificationChannel(num: String) {
        val notificationChannel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel(
                num,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "AlarmManager Tests"
        notificationManager.createNotificationChannel(
            notificationChannel)

    }
}