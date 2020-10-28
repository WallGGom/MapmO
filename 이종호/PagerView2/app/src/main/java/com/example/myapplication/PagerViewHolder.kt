package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.memo_title)
    private val content: TextView = itemView.findViewById(R.id.memo_content)
    private val name: TextView = itemView.findViewById(R.id.memo_name)
    private val created: TextView = itemView.findViewById(R.id.memo_create_at)

    fun bind1(memo: Memo) {
        title.text = memo.title
        content.text = memo.content
        name.text = memo.name
        created.text = memo.created_at
    }

    fun bind2(@ColorRes bgColor: Int, position: Int) {

        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, bgColor))
    }
}