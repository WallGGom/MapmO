package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_pager.view.*
import java.text.SimpleDateFormat

class PagerRecyclerAdapter : RecyclerView.Adapter<PagerRecyclerAdapter.PagerViewHolder>() {
    var helper:MemoRoomHelper? = null
    var listData = mutableListOf<MemoRoom>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val memo = listData.get(position)

        holder.bind1(memo)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mMemo:MemoRoom? = null
        init {
            itemView.buttonDelete.setOnClickListener {
                helper?.memoRoomDao()?.delete(mMemo!!)
                listData.remove(mMemo)
                notifyDataSetChanged()
            }
        }

        fun bind1(memo: MemoRoom) {
            itemView.memo_title.text = memo.title
            itemView.memo_content.text = memo.content
            itemView.memo_place.text = memo.place
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itemView.memo_create_at.text = "${sdf.format(memo.datetime)}"

            this.mMemo = memo
        }

    }

}