package com.example.mapmo.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class NoteModel(@PrimaryKey(autoGenerate = true)
                     var id:Int?, // 메모 번호
                     var noteTitle:String, // 메모 제목
                     var noteDescription:String, // 메모 내용
                     var createdAt: String, // 메모 생성날짜 & 시간
                     var noteColor: Int, // 메모 색상
                     var image: String, // 이미지
                     var voice: String, // 녹음
                     var place: String, // 장소명
                     var latitude: Double, // 위도
                     var longitude: Double, // 경도
                     var planDate: String, // 일정날짜
                     var planTime: String, // 일정시간
                     var alarmCheck: Boolean, // 알림여부
                     var alarmSettime: String, // 알림설정시간
                     var alarmOnOff : Boolean, // 알림 켜기|끄기
                     var alarmMilTime : Long, // 알람 년월일시분 => milsec
                     ) : Serializable
