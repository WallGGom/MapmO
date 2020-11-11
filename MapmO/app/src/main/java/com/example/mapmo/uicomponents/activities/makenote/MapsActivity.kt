package com.example.mapmo.uicomponents.activities.makenote

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.example.mapmo.R
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // 위치값을 사용하기 위해 선언
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // 위치값 요청에 대한 갱신 정보를 받는데 필요(Callback)
    private lateinit var locationCallback: LocationCallback

    var placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

    var pickAddress: String = ""
    var pickLatitude: Double = 0.0
    var pickLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
        // 권한 확인
        checkPermission()
        initPlaces()
        setupPlacesAutocomplete()
//        val intent = Intent(this, MakeNoteActivity::class.java)
//        Log.e("Address", pickAddress)
//        Toast.makeText(this, pickAddress, Toast.LENGTH_SHORT).show()

        btn_save_location.setOnClickListener{
            val returnIntent = Intent()
            returnIntent.putExtra("pickAddress", pickAddress)
            returnIntent.putExtra("pickLatitude", pickLatitude)
            returnIntent.putExtra("pickLongitude", pickLongitude)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private fun setupPlacesAutocomplete() {
        val autocompleteFragment = supportFragmentManager
                .findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(placeFields)

        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                val LATLNG = LatLng(p0.latLng!!.latitude, p0.latLng!!.longitude)
                val markerOptions = MarkerOptions()
                        .position(LATLNG)
                        .title("${p0.name}")

                val cameraPosition = CameraPosition.Builder()
                        .target(LATLNG)
                        .zoom(17.0f)
                        .build()
                Log.e("검색 결과(장소)", p0.name!!)
                pickAddress = p0.name!!
                pickLongitude = p0.latLng!!.longitude
                pickLatitude = p0.latLng!!.latitude
                // 마커 추가 및 카메라 이동
                mMap.addMarker(markerOptions)
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }

            // 오류 발생시 Toast 메시지 출력
            override fun onError(p0: Status) {
                Toast.makeText(this@MapsActivity, ""+p0.statusMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    // 초기
    private fun initPlaces() {
        Places.initialize(this, getString(R.string.places_api))
    }

    // 현재위치 검색
    fun startProcess() {
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        btn_get_current_place.setOnClickListener {
//            mapFragment.getMapAsync(this)
//        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        updateLocation()
        updateLocationWithBtn()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun updateLocation() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            interval = 1000
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for((i, location) in it.locations.withIndex()) {
                        Log.d("First Location", "$i ${location.latitude}, ${location.longitude}")

                        setLastLocation(location)


                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun updateLocationWithBtn() {
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.let {
                    for((i, location) in it.locations.withIndex()) {
                        Log.d("Updated Location", "$i ${location.latitude}, ${location.longitude}")
                        btn_get_current_place.setOnClickListener {
                            setLastLocation(location)
                        }
                        val geocoder = Geocoder(applicationContext, Locale.getDefault())
                        try {
                            val listAddresses: List<Address> = geocoder.getFromLocation(location.latitude, location.longitude, 1)
//                            Log.d("Updated Address", listAddresses.toString())
                            if (listAddresses.size > 0) {
                                val _Location: String = listAddresses[0].getAddressLine(0)
                                Log.d("Updated Location)", "$i ${_Location}")
                                pickAddress = _Location
                                pickLatitude = listAddresses[0].latitude
                                pickLongitude = listAddresses[0].longitude
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun setLastLocation (lastLocation: Location) {
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
        val markerOptions = MarkerOptions()
                .position(LATLNG)
                .title("Here!")

        val cameraPosition = CameraPosition.Builder()
                .target(LATLNG)
                .zoom(17.0f)
                .build()

        mMap.clear()
        mMap.addMarker(markerOptions)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
    val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    val PERM_LOCATION = 99

    fun checkPermission() {
        var permitted_all = true
        for (permission in permissions) {
            val result = ContextCompat.checkSelfPermission(this, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                permitted_all = false
                requestPermission()
                break
            }
        }
        if (permitted_all) {
            startProcess()
        }
    }

    override fun onDestroy() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }

    fun confirmAgain() {
        AlertDialog.Builder(this)
                .setTitle("권한 승인 확인")
                .setMessage("위치 관련 권한을 모두 승인하셔야 앱을 사용할 수 있습니다. 권한 승인을 다시 하시겠습니까?")
                .setPositiveButton("네", { _, _-> requestPermission()})
                .setNegativeButton("아니요", { _, _-> finish()})
                .create()
                .show()
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, PERM_LOCATION)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            99 -> {
                var granted_all = true
                for (result in grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        granted_all = false
                        break
                    }
                }
                if (granted_all) {
                    startProcess()
                } else {
                    confirmAgain()
                }
            }
        }
    }
}