package com.example.mapmo.uicomponents.activities.makenote

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.mapmo.R
import com.example.mapmo.common.Constants
import com.example.mapmo.models.AddNoteViewModel
import com.example.mapmo.models.NoteModel
import com.example.mapmo.models.UpdateNoteViewModel
import com.example.mapmo.uicomponents.base.BaseActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_make_note.*
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("UseSwitchCompatOrMaterialCode")
class MakeNoteActivity : BaseActivity() ,View.OnClickListener {

        // 알람 시간 선택
    var alarm_settime_list = listOf("- 선택하세요 -", "1분전", "5분전", "10분전", "15분전")
    var alarmsettime = ""

//    var helper:MemoRoomHelper? = null
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

    private var mTag:String= MakeNoteActivity::class.java.simpleName
    private var mAddNoteModel: AddNoteViewModel? = null
    private var mEditNoteModel : UpdateNoteViewModel? = null
    private var isEditNote : Boolean = false
    private var mNoteModel: NoteModel? = null
    private var mNoteTitle : TextInputEditText? = null
    private var mNoteDesc : TextInputEditText? = null
    private var mBtnRed : MaterialButton? = null
    private var mBtnGreen : MaterialButton? = null
    private var mBtnBlue : MaterialButton? = null
    private var mBtnYellow : MaterialButton? = null
    private var mBtnDefault : MaterialButton? = null
    private var mNoteColor = -1

    private var mNotePlanDate: TextView? = null
    private var mNotePlanTime: TextView? = null

    var memoAddress: String = ""
    var memoLatitude: Double = 0.0
    var memoLongitude: Double = 0.0
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // 캘린더, 연/월/일 값 생성
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        // 어댑터 생성 & 접혔을 때의 모습을 구성한 Layout을 생성
        var adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alarm_settime_list)
        // 펼쳐졌을 때의 모습을 구성하기 위한 Layout을 지정
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter1

        // spinner에서 선택시 이벤트 발생
        spinner.onItemSelectedListener = selectAlarmsettime

        if (checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)) {
            setViews()
        }

        // 날짜 선택(pickDateBtn 클릭)
        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateTv.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth + ""
                )
            }, year, month, day)
            dpd.show()
        }
        // 시간 선택(pickTimeBtn 클릭)
        pickTimeBtn.setOnClickListener {
            if (dateTv.length() > 0) {
                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    c.set(Calendar.HOUR_OF_DAY, hour)
                    c.set(Calendar.MINUTE, minute)

                    timeTv.text = SimpleDateFormat("HH:mm").format(c.time)
                }
                TimePickerDialog(
                        this,
                        timeSetListener,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true
                ).show()
            } else {
                Toast.makeText(this, "날짜를 먼저 선택해주세요!", Toast.LENGTH_LONG).show()
            }
        }

        switchAlarm.setOnCheckedChangeListener{ CompoundButton, onSwitch ->
            if (timeTv.length() > 0) {
                if (onSwitch) {
                    spinner.isClickable = true
                } else {
                    spinner.isClickable = false
                }
            } else {
                switchAlarm.isChecked = false
                Toast.makeText(this, "날짜/시간을 먼저 선택해주세요!", Toast.LENGTH_LONG).show()
            }
        }

        // 지도검색 버튼을 누르면 MapsActivity로 이동 - intent
        val mapintent = Intent(this, MapsActivity::class.java)
        btnSearch.setOnClickListener{
            startActivityForResult(mapintent, 121)
        }
        // ----------------------------------------
        val actionBar = toolbar
        actionBar!!.title = getString(R.string.create_note)
        setSupportActionBar(actionBar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        fab_make_note.setOnClickListener(this)
        mAddNoteModel = AddNoteViewModel(application)
        mEditNoteModel = UpdateNoteViewModel(application)

        mNoteTitle = findViewById(R.id.addNoteTitle)
        mNoteDesc = findViewById(R.id.addNoteDescription)

        mNotePlanDate = findViewById(R.id.dateTv)
        mNotePlanTime = findViewById(R.id.timeTv)

        mNoteModel = intent.extras?.getSerializable(Constants.SELECTED_NOTE) as NoteModel?

        // 수정 여부 : 기본값 false
        isEditNote = intent.getBooleanExtra(Constants.EDIT_ACTION, false)

        // 이미 작성된 메모를 수정할 경우
        if (isEditNote && mNoteModel != null) {
            actionBar!!.title = getString(R.string.edit_note)
            mNoteTitle?.setText(mNoteModel?.noteTitle)
            mNoteDesc?.setText(mNoteModel?.noteDescription)

            if (mNoteModel?.image != null) {
                val uri = mNoteModel?.image!!.toUri()
                imageUri = uri
                imagePreview.setImageURI(uri)
            }

            if (mNoteModel?.place != null) {
                Place.setText(mNoteModel?.place)
            }

            mNoteColor = mNoteModel!!.noteColor
            dateTv.text = mNoteModel?.planDate.toString()
            timeTv.text = mNoteModel?.planTime.toString()

        }
        // 새로운 메모를 작성할 경우(색깔 기본 값)
        else{
            mNoteColor = -1
        }

        // 메모 색상관련 코드
        mBtnRed = findViewById(R.id.btnRed)
        mBtnRed!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_red_light)
        mBtnRed!!.setOnClickListener(this)

        mBtnGreen = findViewById(R.id.btnGreen)
        mBtnGreen!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_green_light)
        mBtnGreen!!.setOnClickListener(this)

        mBtnBlue = findViewById(R.id.btnBlue)
        mBtnBlue!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_blue_light)
        mBtnBlue!!.setOnClickListener(this)

        mBtnYellow = findViewById(R.id.btnYellow)
        mBtnYellow!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.holo_orange_light)
        mBtnYellow!!.setOnClickListener(this)

        mBtnDefault = findViewById(R.id.btnDefault)
        mBtnDefault!!.backgroundTintList = ContextCompat.getColorStateList(this@MakeNoteActivity, android.R.color.transparent)
        mBtnDefault!!.setOnClickListener(this)

        mNoteTitle!!.requestFocus()
    }

    val selectAlarmsettime = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(parent?.id){
                R.id.spinner -> {
                    alarmsettime = alarm_settime_list[position]
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
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap) : Uri? {
        var values = ContentValues()
        // 저장할 파일이름과 마임타입 설정
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        
        // Q이상이면 다른 곳에서 내가 사용하는 데이터 요청 무시
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
//                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                        values.clear()
//                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
//                        contentResolver.update(uri, values, null, null)
//                    }
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e("File", "error=${e.localizedMessage}")
        }
        return uri
    }

    // 촬영한 사진은 중복되지 않도록 이름 설정
    fun newFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    // 전달된 비트맵을 saveImageFile에서 전처리하고, 반환된 Uri를 이미지뷰에 세팅
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if (data?.extras?.get("data") != null) {
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val uri = saveImageFile(newFileName(), "image/jpg", bitmap)
                        showToast("${uri}")
                        imagePreview.setImageURI(uri)
                        imageUri = uri

                    }
                }
                FLAG_REQ_STORAGE -> {
                    val uri = data?.data
                    imagePreview.setImageURI(uri)
//                    showToast("${uri}")

                    imageUri = uri
                    Log.e("나오나요??", "${imageUri}")
                    Log.e("나오나요??", imageUri.toString())
                }
                121 -> {
                    val pickAddress = data?.getStringExtra("pickAddress")
                    val pickLatitude = data?.getDoubleExtra("pickLatitude", 0.0)
                    val pickLongitude = data?.getDoubleExtra("pickLongitude", 0.0)
                    memoAddress = pickAddress!!
                    memoLatitude = pickLatitude!!
                    memoLongitude = pickLongitude!!

                    Place.text = pickAddress
//                    Toast.makeText(this, "${pickLatitude}", Toast.LENGTH_LONG).show()
//                    Toast.makeText(this, "${pickLatitude}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflator = menuInflater
        inflator.inflate(R.menu.create_note_menu, menu)
        return true
    }

    @SuppressLint("NewApi")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId)
        {
            R.id.action_save ->
                validateAndSaveNote()
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NewApi")
    override fun onClick(v: View) {
        when(v.id){
            R.id.fab_make_note ->
                validateAndSaveNote()

            R.id.btnRed -> {
                mNoteColor = 1
                showToast("빨강색 선택")
            }

            R.id.btnGreen -> {
                mNoteColor = 2
                showToast("초록색 선택")
            }

            R.id.btnBlue -> {
                mNoteColor = 3
                showToast("파랑색 선택")
            }

            R.id.btnYellow -> {
                mNoteColor = 4
                showToast("노랑색 선택")
            }

            R.id.btnDefault -> {
                mNoteColor = -1
                showToast("흰색 선택")
            }

            else -> {
                mNoteColor = -1
            }
        }
    }

    // 유효성검사 및 메모저장
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateAndSaveNote(){
        // 제목 미입력시 작성 유도
        if (!mAppUtils.isInputEditTextFilled(addNoteTitle!!, addNoteLayout!!, getString(R.string.note_title_error))) {
            return
        }
        else {
            var timeInMilliseconds: Long = 0
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
            val formatted = current.format(formatter)
            mAppLogger.debug(mTag, "Proceed with saving to DB ")
            Log.e("temp_check", dateTv.text.toString())
            Log.e("temp_check", timeTv.text.toString())
            if ((dateTv.text.toString() != "") and (timeTv.text.toString() != "")) {
                val mixDate = dateTv.text.toString() + " " + timeTv.text.toString()
                Log.e("DDDDDDDDD", mixDate)
                val form = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.KOREA)
                val localDate = LocalDateTime.parse(mixDate, form)
                timeInMilliseconds = localDate.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli()
                Log.e("DDDDDDDDD", "Date in milli :: FOR API >= 26 >>> $timeInMilliseconds")
            }

            // 존재하는 메모 수정 후 저장
            if(isEditNote){
                mNoteModel?.noteTitle = addNoteTitle.text.toString()
                mNoteModel?.noteDescription = addNoteDescription.text.toString()
                mNoteModel?.noteColor = mNoteColor
                mNoteModel?.image = imageUri.toString()
                mNoteModel?.voice = ""
                if (memoAddress == "") {
                    mNoteModel?.place = mNoteModel!!.place

                } else {
                    mNoteModel?.place = memoAddress
                }

                mNoteModel?.latitude = memoLatitude
                mNoteModel?.longitude = memoLongitude
                mNoteModel?.planDate = dateTv.text.toString()
                mNoteModel?.planTime = timeTv.text.toString()
                mNoteModel?.alarmCheck = switchAlarm.isChecked
                mNoteModel?.alarmSettime = ""
                mNoteModel?.alarmOnOff = false
                mNoteModel?.alarmMilTime = timeInMilliseconds
                mNoteModel?.let { mEditNoteModel?.updateNote(it) }
            }
            // 새로운 메모 저장
            else {
                var noteModel = NoteModel(
                        null,
                        addNoteTitle.text.toString(),
                        addNoteDescription.text.toString(),
                        formatted,
                        mNoteColor,
                        imageUri.toString(),
                        "",
                        Place.text.toString(),
                        memoLatitude,
                        memoLongitude,
                        dateTv.text.toString(),
                        timeTv.text.toString(),
                        switchAlarm.isChecked,
                        "",
                        false,
                        timeInMilliseconds,
                )
                mAddNoteModel?.addNote(noteModel)
            }
            closeActivity()
        }
//        showToast("${imageUri.toString()}")
    }

    private fun closeActivity(){
        finish()
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
