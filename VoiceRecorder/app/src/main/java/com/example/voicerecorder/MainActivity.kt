package com.example.audio

import android.Manifest
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mediaRecorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var state = false
        var recordingStopped = false

        val output = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}" + "/test.mp3"

        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissions,0)

        button_start_recording.setOnClickListener {
            mediaRecorder = MediaRecorder()

            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output)

            mediaRecorder?.prepare()
            mediaRecorder?.start()

            state = true

            Toast.makeText(this, "녹음 시작", Toast.LENGTH_SHORT).show()
        }

        button_stop_recording.setOnClickListener{
            if(state){
                mediaRecorder?.stop()
                mediaRecorder?.release()

                state = false

                Toast.makeText(this, "녹음 중지", Toast.LENGTH_SHORT).show()
            }
        }

        button_pause_recording.setOnClickListener {
            if (state) {
                if (!recordingStopped) {
                    Toast.makeText(this, "녹음 정지", Toast.LENGTH_SHORT).show()
                    mediaRecorder?.pause()
                    recordingStopped = true
                    button_pause_recording.text = "다시 시작"
                } else {
                    Toast.makeText(this,"다시 시작", Toast.LENGTH_SHORT).show()
                    mediaRecorder?.resume()
                    recordingStopped = false
                    button_pause_recording.text = "정지"
                }
            }
        }
    }
}