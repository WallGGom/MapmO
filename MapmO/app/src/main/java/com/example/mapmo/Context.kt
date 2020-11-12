package com.example.mapmo

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.net.Uri
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.AlarmReceiver
import com.example.mapmo.uicomponents.activities.splash.SplashActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.math.pow

const val OPEN_TAB = "open_tab"
const val TAB_ALARM = 0
const val OPEN_ALARMS_TAB_INTENT_ID = 9996
const val ALARM_ID = "alarm_id"

fun Context.scheduleNextAlarm(note: NoteModel, showToast: Boolean) {
//    val calendar = Calendar.getInstance()
//    calendar.firstDayOfWeek = Calendar.MONDAY
//        val currentDay = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
        val current = LocalDateTime.now()
        val currentMilTime = current.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
        val diffMill = (note.alarmMilTime - currentMilTime).toInt()
//        val isCorrectDay = note.days and 2.0.pow(currentDay).toInt() != 0
//        val currentTimeInMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        if (note.alarmCheck && diffMill > 0) {
            setupAlarmClock(note, diffMill)
        }
}

fun Context.setupAlarmClock(note: NoteModel, triggerInSeconds: Int) {
    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val targetMS = System.currentTimeMillis() + triggerInSeconds * 1000
    AlarmManagerCompat.setAlarmClock(alarmManager, targetMS, getOpenAlarmTabIntent(), getAlarmIntent(note))
}

fun Context.getOpenAlarmTabIntent(): PendingIntent {
    val intent = getLaunchIntent() ?: Intent(this, SplashActivity::class.java)
    intent.putExtra(OPEN_TAB, TAB_ALARM)
    return PendingIntent.getActivity(this, OPEN_ALARMS_TAB_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}


fun Context.getAlarmIntent(note: NoteModel): PendingIntent {
    val intent = Intent(this, AlarmReceiver::class.java)
    intent.putExtra(ALARM_ID, note.id)
    return PendingIntent.getBroadcast(this, note.id!!, intent, PendingIntent.FLAG_UPDATE_CURRENT)
}

fun Context.showAlarmNotification(note: NoteModel) {
    val pendingIntent = getOpenAlarmTabIntent()
    val notification = getAlarmNotification(pendingIntent, note)
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(alarm.id, notification)
    scheduleNextAlarm(alarm, false)
}

@SuppressLint("NewApi")
fun Context.getTimerNotification(pendingIntent: PendingIntent, addDeleteIntent: Boolean): Notification {
    var soundUri = config.timerSoundUri
    if (soundUri == SILENT) {
        soundUri = ""
    } else {
        grantReadUriPermission(soundUri)
    }

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = config.timerChannelId ?: "simple_timer_channel_${soundUri}_${System.currentTimeMillis()}"
    config.timerChannelId = channelId

    if (isOreoPlus()) {
        try {
            notificationManager.deleteNotificationChannel(channelId)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setLegacyStreamType(AudioManager.STREAM_ALARM)
            .build()

        val name = getString(R.string.timer)
        val importance = NotificationManager.IMPORTANCE_HIGH
        NotificationChannel(channelId, name, importance).apply {
            setBypassDnd(true)
            enableLights(true)
            lightColor = getAdjustedPrimaryColor()
            setSound(Uri.parse(soundUri), audioAttributes)

            if (!config.timerVibrate) {
                vibrationPattern = longArrayOf(0L)
            }

            enableVibration(true)
            notificationManager.createNotificationChannel(this)
        }
    }

    val reminderActivityIntent = getReminderActivityIntent()
    val builder = NotificationCompat.Builder(this)
        .setContentTitle(getString(R.string.timer))
        .setContentText(getString(R.string.time_expired))
        .setSmallIcon(R.drawable.ic_timer)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_LIGHTS)
        .setCategory(Notification.CATEGORY_EVENT)
        .setAutoCancel(true)
        .setSound(Uri.parse(soundUri), AudioManager.STREAM_ALARM)
        .setChannelId(channelId)
        .addAction(R.drawable.ic_cross_vector, getString(R.string.dismiss), if (addDeleteIntent) reminderActivityIntent else getHideTimerPendingIntent())

    if (addDeleteIntent) {
        builder.setDeleteIntent(reminderActivityIntent)
    }

    builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

    if (config.timerVibrate) {
        val vibrateArray = LongArray(2) { 500 }
        builder.setVibrate(vibrateArray)
    }

    val notification = builder.build()
    notification.flags = notification.flags or Notification.FLAG_INSISTENT
    return notification
}

fun Context.hideNotification(id: Int) {
    val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager.cancel(id)
}