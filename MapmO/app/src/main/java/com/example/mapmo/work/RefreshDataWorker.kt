package com.example.mapmo.work

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
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



    override suspend fun doWork(): Result {



        try {
//            checkPermission()
            checkDistance()
        } catch(err: Exception){
            Log.e("work", err.toString())
            Result.retry()
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.example.mapmo.work.RefreshDataWorker"
    }



//    var locationCallback = object : LocationCallback() {}
//    var locationRequest = LocationRequest.create()
    private fun checkDistance() {
        Log.e("enter", "진입1")
        try {
            Log.e("enter", "진입2")
//            locationRequest.run {
//                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
////            interval = 1000
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
            //마지막 위치를 가져오는데에 성공한다면

            mFusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if(location == null) {
                        Log.e("location", "location get fail")
                    } else {
                        Log.d("location", "${location.latitude} , ${location.longitude}")
                    }
                }
                .addOnFailureListener {
                    Log.e("location", "location error is ${it.message}")
                    it.printStackTrace()
                }
                .addOnCompleteListener { task ->
                if( task.isSuccessful){
                    Log.e("location", "task 진입")
//                    Log.e("location", task.result.latitude.toString())

                    task.result?.let{ aLocation ->
                        val fromLat = aLocation.latitude
                        val fromLng = aLocation.longitude

                        val results = FloatArray(1)
                        /*  WGS-84 타원체 계산 (m)단위의 대략적인 정보이므로, 실제거리와 차이가 있을 수 있다  */
//                        if (aNotiLng != null && aNotiLat != null) {
//                            Location.distanceBetween(
//                                fromLat,
//                                fromLng,
//                                aNotiLat.toDouble(),
//                                aNotiLng.toDouble(),
//                                results
//                            )
//                        }
                        Log.e("work latitude", fromLat.toString())
                        Log.e("work longitude", fromLng.toString())
                        Log.e("work result", results.toString())
                        //300m 이내일 경우
//                        if(results[0] < 300){
//                            /* 프로젝트의 내용이므로 생략 */
//                        }else{
//
//                        }
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
}