package com.example.mapmo.work

import android.Manifest
import android.app.Notification
import android.app.Notification.DEFAULT_ALL
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Color.RED
import android.location.Location
import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION
import android.media.AudioAttributes.USAGE_NOTIFICATION_RINGTONE
import android.media.RingtoneManager.TYPE_NOTIFICATION
import android.media.RingtoneManager.getDefaultUri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import android.os.Looper
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mapmo.R
import com.example.mapmo.common.Constants
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.models.NoteModel
import com.example.mapmo.uicomponents.activities.landing.MainActivity
import com.example.mapmo.uicomponents.activities.viewnote.ViewNote
import com.google.android.gms.location.*
import java.lang.Exception
import java.text.SimpleDateFormat


class RefreshDataWorker(appContext: Context, params: WorkerParameters):
        CoroutineWorker(appContext, params){

    private val mContext = appContext
    //위치 가져올때 필요
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(mContext)
    }
    var mNoteDataBase : NoteDataBase? = null
    var mNoteList: MutableList<NoteModel>? = null
    var tempNoteList: MutableList<NoteModel> = mutableListOf()
    val NOTIFICATION_CHANNEL_ID = "222"
    val NOTIFICATION_CHANNEL_NAME = "MapmO"
    var notificationBuilder: NotificationCompat.Builder
            = NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL_ID)
            .apply {
                setSmallIcon(R.drawable.logo)
                setDefaults(Notification.DEFAULT_ALL)
                setContentTitle("제목")
                setContentText("내용내용내용")
                setAutoCancel(false)
                setWhen(System.currentTimeMillis())
                priority = NotificationCompat.PRIORITY_MAX
                setContentIntent(pendingIntent)
            }
    var notificationIntent = Intent(mContext, MainActivity::class.java).apply {
        var flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    var pendingIntent: PendingIntent = PendingIntent.getActivity( mContext, 0, notificationIntent, 0)
    private val notificationManager: NotificationManager by lazy {
        mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    val id = inputData.getLong(NOTIFICATION_ID, 0).toInt()
    override suspend fun doWork(): Result {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID,
//                    NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE).apply {
//                description = "MapmO"
//                lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//            }
//            notificationManager.createNotificationChannel(notificationChannel)
//        }

        try {
//            checkPermission()
            checkDistance()


//            locationRequest.run {
//                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//                interval = 1000
//            }
//
//            locationCallback = object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult?) {
//                    locationResult?.let {
//                        for((i, location) in it.locations.withIndex()) {
//                            Log.e("First Location", "$i ${location.latitude}, ${location.longitude}")
//                        }
//                    }
//                }
//            }
//            runOnUiThread
//            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
        } catch(err: Exception){
            Log.e("work", err.toString())
            Result.retry()
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.example.mapmo.work.RefreshDataWorker"
        const val NOTIFICATION_ID = "appName_notification_id"
        const val NOTIFICATION_NAME = "appName"
        const val NOTIFICATION_CHANNEL = "appName_channel_01"
        const val NOTIFICATION_WORK = "appName_notification_work"
    }

    var locationCallback = object : LocationCallback() {}
    var locationRequest = LocationRequest.create()
    private fun checkDistance() {
        Log.e("enter", "진입1")
        try {
            Log.e("enter", "진입2")

            //마지막 위치를 가져오는데에 성공한다면

            mFusedLocationClient.lastLocation
//                    .addOnSuccessListener { location ->
//                        if(location == null) {
//                            Log.e("location", "location get fail")
//                        } else {
//                            Log.d("location", "${location.latitude} , ${location.longitude}")
//                        }
//                    }
//                    .addOnFailureListener {
//                        Log.e("location", "location error is ${it.message}")
//                        it.printStackTrace()
//                    }
                    .addOnCompleteListener { task ->
                        if( task.isSuccessful){
                            Log.e("location", "task 진입")
//                    Log.e("location", task.result.latitude.toString())

                            task.result?.let{ aLocation ->
                                val fromLat = aLocation.latitude
                                val fromLng = aLocation.longitude
                                val results = FloatArray(1)
                                mNoteDataBase = NoteDataBase.getInstance(mContext)
                                var addRunnable = Runnable {
                                    try {
                                        mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAll()
                                        locationRequest.run {
                                            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                                            interval = 100000
                                        }

                                        locationCallback = object : LocationCallback() {
                                            override fun onLocationResult(locationResult: LocationResult?) {
                                                locationResult?.let {
                                                    for((i, location) in it.locations.withIndex()) {
                                                        Log.e("First Location", "$i ${location.latitude}, ${location.longitude}")


                                                    }
                                                }
                                            }
                                        }
                                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

                                        Log.e("work list", mNoteList.toString())

                                        for (pick in mNoteList!!) {
                                            Log.e("work pick", "${ pick.latitude } ${ pick.longitude }")
                                            Location.distanceBetween(
                                                fromLat,
                                                fromLng,
                                                pick.latitude,
                                                pick.longitude,
                                                results
                                            )
                                            Log.e("results1", tempNoteList.toString())
                                            Log.e("results2", results[0].toString())
                                            if ((results[0] < 500) and (pick !in tempNoteList)) {
                                                sendNotification(pick.id!!, pick)
                                                tempNoteList.add(pick)
                                                Log.e("results3", tempNoteList.toString())
                                            }

                                        }

                                    } catch (e: Exception) {
                                        Log.d("tag", "Error - $e")
                                    }
                                }
                                var addThread = Thread(addRunnable)
                                addThread.start()

                                /*  WGS-84 타원체 계산 (m)단위의 대략적인 정보이므로, 실제거리와 차이가 있을 수 있다  */
//                                if (aNotiLng != null && aNotiLat != null) {
//                                    Location.distanceBetween(
//                                        fromLat,
//                                        fromLng,
//                                        aNotiLat.toDouble(),
//                                        aNotiLng.toDouble(),
//                                        results
//                                    )
//                                }
//                                if(results[0] < 300){
//                            /* 프로젝트의 내용이므로 생략 */
//                        }else{
//
//                        }
                                Log.e("work latitude", fromLat.toString())
                                Log.e("work longitude", fromLng.toString())
                                Log.e("work result", results.toString())
//                                notificationManager.notify(0, notificationBuilder.build())
                                //300m 이내일 경우
//
                            }
                        } else {
                            val timeNow = System.currentTimeMillis()
                            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")

                            Log.e("work", sdf.format(timeNow))
                            Log.e("work", task.exception.toString())

                        }
                    }
        }catch (err: SecurityException) { Log.e("tag", "Security", err) }
    }

    private fun sendNotification(id: Int, Note: NoteModel) {
        val intent = Intent(applicationContext, ViewNote::class.java)
        val bundle = Bundle()
        intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
        bundle.putSerializable(Constants.SELECTED_NOTE,Note)
        intent.putExtras(bundle)

        val notificationManager =
                applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


//        var titleNotification = applicationContext.getString(R.string.notification_title)
//        var subtitleNotification = applicationContext.getString(R.string.notification_subtitle)
        val pendingIntent = getActivity(applicationContext, 0, intent, 0)
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(Note.place).setContentText(Note.noteDescription)
                .setDefaults(DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true)

        notification.priority = PRIORITY_MAX

        if (SDK_INT >= O) {
            notification.setChannelId(NOTIFICATION_CHANNEL)

            val ringtoneManager = getDefaultUri(TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder().setUsage(USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(CONTENT_TYPE_SONIFICATION).build()

            val channel =
                    NotificationChannel(NOTIFICATION_CHANNEL, NOTIFICATION_NAME, IMPORTANCE_HIGH)

            channel.enableLights(true)
            channel.lightColor = RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            channel.setSound(ringtoneManager, audioAttributes)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification.build())
    }
}

