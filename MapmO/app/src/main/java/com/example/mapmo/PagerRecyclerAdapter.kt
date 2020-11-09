package com.example.mapmo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pager.view.*
import java.text.SimpleDateFormat

// RecyclerAdapter (RecyclerView에 적용할 Adapter 클래스 생성)
class MemoRecyclerAdapter(val memos: MutableList<MemoRoom>, val type: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var helper:MemoRoomHelper? = null
////    // listData는 MemoRoom에 저장된 데이터들로 사용
////    var listData = mutableListOf<MemoRoom>()

    // getItemViewType의 리턴값 Int가 viewType으로 넘어온다.
    // viewType으로 넘어오는 값에 따라 viewHolder를 알맞게 처리해주면 된다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.day_memo_item, parent, false)
                DayViewHolder(view)
            }
            2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.week_memo_item, parent, false)
                WeekViewHolder(view)
            }
            3 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.month_memo_item, parent, false)
                MonthViewHolder(view)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            1 -> {
                holder as DayViewHolder
                holder.bind(memos[position])
            }
            2 -> {
                holder as WeekViewHolder
                holder.bind(memos[position])
            }
            3 -> {
                holder as MonthViewHolder
                holder.bind(memos[position])
            }

        }
    }


    // 데이터 개수 불러오는 함수
    override fun getItemCount(): Int {
        return memos.size
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo:MemoRoom? = null
//        val btnDel = itemView.findViewById<Button>(R.id.btnDelete)
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                memos.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }

        val titleTv1 = itemView.findViewById<TextView>(R.id.day_memoTitle1)
        val contentTv1 = itemView.findViewById<TextView>(R.id.day_memoContent1)
        val alarmTv1 = itemView.findViewById<TextView>(R.id.day_alarm)

        fun bind(memo: MemoRoom) {
            titleTv1.text = memo.title
            contentTv1.text = memo.content
            if (memo.alarmcheck) {
                alarmTv1.text = "On"
                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
            } else {
                alarmTv1.text = "Off"
            }

            this.mMemo = memo
        }

//        var mMemo:MemoRoom? = null
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                listData.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }
//
//        fun bind(memo: MemoRoom) {
//            itemView.memo_title.text = memo.title
//            itemView.memo_content.text = memo.content
//            itemView.memo_place.text = memo.place
//
//            // 작성시간 형식(날짜 시간)
//            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
//            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"
//
//            // 알림 설정 시간 & 여부
//            itemView.memo_alarm_settime.text = memo.alarmsettime
//            if (memo.alarmcheck) {
//                itemView.memo_alarm_check.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                itemView.memo_alarm_check.text = "Off"
//            }
//
//            this.mMemo = memo
    }

    inner class WeekViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo:MemoRoom? = null
//        val btnDel = itemView.findViewById<Button>(R.id.btnDelete)
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                memos.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }

        val titleTv2 = itemView.findViewById<TextView>(R.id.week_memoTitle2)
        val contentTv2 = itemView.findViewById<TextView>(R.id.week_memoContent2)
        val alarmTv2 = itemView.findViewById<TextView>(R.id.week_alarm2)

        fun bind(memo: MemoRoom) {
            titleTv2.text = memo.title
            contentTv2.text = memo.content
            if (memo.alarmcheck) {
                alarmTv2.text = "On"
                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
            } else {
                alarmTv2.text = "Off"
            }

            this.mMemo = memo
        }

//        var mMemo:MemoRoom? = null
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                listData.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }
//
//        fun bind(memo: MemoRoom) {
//            itemView.memo_title.text = memo.title
//            itemView.memo_content.text = memo.content
//            itemView.memo_place.text = memo.place
//
//            // 작성시간 형식(날짜 시간)
//            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
//            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"
//
//            // 알림 설정 시간 & 여부
//            itemView.memo_alarm_settime.text = memo.alarmsettime
//            if (memo.alarmcheck) {
//                itemView.memo_alarm_check.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                itemView.memo_alarm_check.text = "Off"
//            }
//
//            this.mMemo = memo
    }

    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo:MemoRoom? = null
//        val btnDel = itemView.findViewById<Button>(R.id.btnDelete)
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                memos.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }

        val titleTv3 = itemView.findViewById<TextView>(R.id.month_memoTitle1)
        val contentTv3 = itemView.findViewById<TextView>(R.id.month_memoContent1)
        val alarmTv3 = itemView.findViewById<TextView>(R.id.month_alarm)

        fun bind(memo: MemoRoom) {
            titleTv3.text = memo.title
            contentTv3.text = memo.content
            if (memo.alarmcheck) {
                alarmTv3.text = "On"
                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
            } else {
                alarmTv3.text = "Off"
            }

            this.mMemo = memo
        }

//        var mMemo:MemoRoom? = null
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                listData.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }
//
//        fun bind(memo: MemoRoom) {
//            itemView.memo_title.text = memo.title
//            itemView.memo_content.text = memo.content
//            itemView.memo_place.text = memo.place
//
//            // 작성시간 형식(날짜 시간)
//            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
//            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"
//
//            // 알림 설정 시간 & 여부
//            itemView.memo_alarm_settime.text = memo.alarmsettime
//            if (memo.alarmcheck) {
//                itemView.memo_alarm_check.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                itemView.memo_alarm_check.text = "Off"
//            }
//
//            this.mMemo = memo
    }

//    inner class MemoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var mMemo:MemoRoom? = null
//        init {
//            // 삭제버튼 클릭 = 메모 삭제
//            itemView.btnDelete.setOnClickListener {
//                helper?.memoRoomDao()?.delete(mMemo!!)
//                listData.remove(mMemo)
//                notifyDataSetChanged()
//            }
//        }
//
//        // 바인딩 시키는 내용 (제목, 내용, 장소)
//        fun bind(memo: MemoRoom) {
//            itemView.memo_title.text = memo.title
//            itemView.memo_content.text = memo.content
//            itemView.memo_place.text = memo.place
//
//            // 작성시간 형식(날짜 시간)
//            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
//            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"
//
//            // 알림 설정 시간 & 여부
//            itemView.memo_alarm_settime.text = memo.alarmsettime
//            if (memo.alarmcheck) {
//                itemView.memo_alarm_check.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
//                itemView.memo_alarm_date.text = memo.alarmdate
//                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                itemView.memo_alarm_check.text = "Off"
//            }
//
//            this.mMemo = memo
//        }
//
//    }

}
