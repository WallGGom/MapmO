package com.example.mapmo

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.month_date_item.view.*
import kotlinx.android.synthetic.main.week_date_item.view.*

class WeekDateAdapter : RecyclerView.Adapter<WeekHolder>() {
    var listData = mutableListOf<ListWeekData>()
    var step: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_date_item, parent, false)
        return WeekHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: WeekHolder, position: Int) {
        step=position
        var data = listData.get(position)
        holder.bind(data, step)
    }
}

class WeekHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(listdata: ListWeekData, step:Int) {
        itemView.week_date.text = "${listdata.number}"
        if (step % 7 == 0) {
            itemView.week_date.setTextColor(Color.parseColor("#FF0000"))
        } else if (step % 7 == 6) {
            itemView.week_date.setTextColor(Color.parseColor("#0000FF"))
        }
    }
}