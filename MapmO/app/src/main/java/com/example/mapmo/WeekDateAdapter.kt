package com.example.mapmo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.week_date_item.view.*

class WeekDateAdapter : RecyclerView.Adapter<WeekHolder>() {
    var listData = mutableListOf<ListWeekData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_date_item, parent, false)
        return WeekHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: WeekHolder, position: Int) {
        var data = listData.get(position)
        holder.bind(data)
    }
}

class WeekHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(listdata: ListWeekData) {
        itemView.week_date.text = "${listdata.number}"
    }
}