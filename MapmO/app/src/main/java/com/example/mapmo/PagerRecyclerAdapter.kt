package com.example.mapmo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pager.view.*
import java.text.SimpleDateFormat

// RecyclerAdapter (RecyclerView에 적용할 Adapter 클래스 생성)
class PagerRecyclerAdapter : RecyclerView.Adapter<PagerRecyclerAdapter.PagerViewHolder>() {
    var helper:MemoRoomHelper? = null
    // listData는 MemoRoom에 저장된 데이터들로 사용
    var listData = mutableListOf<MemoRoom>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false))

    // ???
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val memo = listData.get(position)
        holder.bind1(memo)
    }

    // 데이터 개수 불러오는 함수
    override fun getItemCount(): Int {
        return listData.size
    }


    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo:MemoRoom? = null
        init {
            // 삭제버튼 클릭 = 메모 삭제
            itemView.btnDelete.setOnClickListener {
                helper?.memoRoomDao()?.delete(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        // 바인딩 시키는 내용 (제목, 내용, 장소)
        fun bind1(memo: MemoRoom) {
            itemView.memo_title.text = memo.title
            itemView.memo_content.text = memo.content
            itemView.memo_place.text = memo.place

            // 작성시간 형식(날짜 시간)
            val sdf = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분")
            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"

            // 알림 설정 시간 & 여부
            itemView.memo_alarm_settime.text = memo.alarmsettime
            if (memo.alarmcheck) {
                itemView.memo_alarm_check.text = "On"
                // 메모 실행(계획) 날짜 & 시간
                itemView.memo_alarm_date.text = memo.alarmdate
                itemView.memo_alarm_time.text = memo.alarmtime
            } else {
                itemView.memo_alarm_check.text = "Off"
            }

            this.mMemo = memo
        }

    }

}
