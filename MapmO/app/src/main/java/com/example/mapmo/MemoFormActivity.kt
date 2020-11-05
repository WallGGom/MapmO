package com.example.mapmo

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_memo_form.*
import kotlinx.android.synthetic.main.item_pager.*
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MemoFormActivity : AppCompatActivity() {

    // 알람 시간 선택
    var alarm_settime_list = listOf("- 선택하세요 -", "1분전", "5분전", "10분전", "15분전")
    var alarmSettime = ""

    var helper:MemoRoomHelper? = null
    val random = Random()
    private fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    // 권한 플래그값 정의
    val FLAG_PERM_CAMERA = 98
    val FLAG_PERM_STORAGE = 99

    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    override fun onCreate(savedInstanceState: Bundle?) {

        // 캘린더, 연/월/일 값 생성
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_form)

        // 어댑터 생성 & 접혔을 때의 못브을 구성한 Layout을 생성
        var adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarm_settime_list)
        // 펼쳐졌을 때의 못브을 구성하기 위한 Layout을 지정
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter1

        // spinner에서 선택시 이벤트 발생
        spinner.onItemSelectedListener = selectAlarmsettime

        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
            setViews()
        }

        // 날짜 선택(pickDateBtn 클릭)
        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateTv.setText(""+ year + "년" + (month+1) + "월" + dayOfMonth + "일"
            )
            }, year, month, day)

            dpd.show()
        }
        // 시간 선택(pickTimeBtn 클릭)
        pickTimeBtn.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)

                timeTv.text = SimpleDateFormat("HH시 mm분").format(c.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                true
            ).show()

        }

        switchAlarm.setOnCheckedChangeListener{CompoundButton, onSwitch ->
            if (onSwitch) {
                spinner.isClickable = true
            } else {
                spinner.isClickable = false
            }

        }

        // MemoFrom 액티비티를 intent로 설정
        val mainintent = Intent(this, MainActivity::class.java)

        // 메모생성 버튼을 누르면 startActivity동작 - intent
        btnClose.setOnClickListener{ startActivity(mainintent) }

        // 지도검색 버튼을 누르면 MapsActivity로 이동 - intent
        val mapintent = Intent(this, MapsActivity::class.java)
        btnSearch.setOnClickListener{
            startActivityForResult(mapintent, 121)
            finish()
        }


        helper = Room.databaseBuilder(this, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()

        // 메모 저장 버튼 클릭이벤트
        btnSave.setOnClickListener {
            // Log.d("What the type", "${dateTv.text}")
            if (Title.text.toString().isNotEmpty() && Content.text.toString().isNotEmpty()) {
                val memo = MemoRoom(Title.text.toString(), Content.text.toString(), Place.text.toString(), System.currentTimeMillis(), 0, 0, dateTv.text.toString(), timeTv.text.toString(), switchAlarm.isChecked, alarmSettime)
                helper?.memoRoomDao()?.insert(memo)
                Log.d("memo", memo.title)
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                Toast.makeText(this, "제목과 내용은 필수로 입력하셔야 합니다.", Toast.LENGTH_LONG).show()
            }
        }


    }
    val selectAlarmsettime = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(parent?.id){
                R.id.spinner -> {
                    alarmSettime = alarm_settime_list[position]
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }

    fun setViews(){
        buttonCamera.setOnClickListener {
            openCamera()
        }
        buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    fun openCamera() {
        if (checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, FLAG_REQ_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
//                        imagePreview.setImageBitmap(bitmap)
                        val uri = saveImageFile(newFileName(), "image/jpg", bitmap)
                        imagePreview.setImageURI(uri)
                    }
                }
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data
                    imagePreview.setImageURI(uri)
                }
            }
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    fun saveImageFile(filename:String, mimeType:String, bitmap: Bitmap) : Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try {
            if(uri != null) {
                var descriptor = contentResolver.openFileDescriptor(uri, "w")
                if(descriptor != null) {
                    val fos = FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(uri, values, null, null)
                    }
                }
            }
        }catch (e:java.lang.Exception) {
            Log.e("File", "error=${e.localizedMessage}")
        }
        return uri
    }

    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())

        return "$filename.jpg"
    }

    // 여기서 부터 권한처리 관련 함수
    fun checkPermission(permissions: Array<out String>, flag: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, flag)
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FLAG_PERM_STORAGE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "저장소 권한을 승인해야지만 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
            FLAG_PERM_CAMERA -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "카메라 권한을 승인해야지만 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}