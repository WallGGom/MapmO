package com.example.mapmo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.models.NoteModel

import java.text.SimpleDateFormat

// RecyclerAdapter (RecyclerView에 적용할 Adapter 클래스 생성)
class MemoRecyclerAdapter(val memos: MutableList<NoteModel>?, val type: Int, val itemClick: (NoteModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    var helper: NoteDataBase? = null
////    // listData는 MemoRoom에 저장된 데이터들로 사용
////    var listData = mutableListOf<MemoRoom>()

    // getItemViewType의 리턴값 Int가 viewType으로 넘어온다.
    // viewType으로 넘어오는 값에 따라 viewHolder를 알맞게 처리해주면 된다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View?
        return when (viewType) {
            2 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.week_memo_item, parent, false)
                WeekViewHolder(view, itemClick)
            }
            3 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.month_memo_item, parent, false)
                MonthViewHolder(view, itemClick)
            }
            else -> throw RuntimeException("알 수 없는 뷰 타입 에러")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {

            2 -> {
                holder as WeekViewHolder
                memos?.get(position)?.let { holder.bind(it) }
            }
            3 -> {
                holder as MonthViewHolder
                memos?.get(position)?.let { holder.bind(it) }
            }

        }
    }


    // 데이터 개수 불러오는 함수
    override fun getItemCount(): Int {
        if (memos != null) {
            return memos.size
        }
        return 0
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }



    inner class WeekViewHolder(itemView: View, itemClick: (NoteModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        var mMemo:NoteModel? = null
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
//        val alarmTv2 = itemView.findViewById<TextView>(R.id.week_alarm2)

        fun bind(memo: NoteModel) {
            titleTv2.text = memo.noteTitle
            contentTv2.text = memo.noteDescription
//            if (memo.alarmCheck) {
//                alarmTv2.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
////                itemView.memo_alarm_date.text = memo.alarmdate
////                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                alarmTv2.text = "Off"
//            }

            this.mMemo = memo
            itemView.setOnClickListener{ itemClick(memo) }
        }


    }

    inner class MonthViewHolder(itemView: View, itemClick: (NoteModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        var mMemo: NoteModel? = null


        val titleTv3 = itemView.findViewById<TextView>(R.id.month_memoTitle1)
        val contentTv3 = itemView.findViewById<TextView>(R.id.month_memoContent1)
//        val alarmTv3 = itemView.findViewById<TextView>(R.id.month_alarm)

        fun bind(memo: NoteModel) {
            titleTv3.text = memo.noteTitle
            contentTv3.text = memo.noteDescription
//            if (memo.alarmCheck) {
//                alarmTv3.text = "On"
//                // 메모 실행(계획) 날짜 & 시간
////                itemView.memo_alarm_date.text = memo.alarmdate
////                itemView.memo_alarm_time.text = memo.alarmtime
//            } else {
//                alarmTv3.text = "Off"
//            }

            this.mMemo = memo
            itemView.setOnClickListener { itemClick(memo) }
        }


    }
}

