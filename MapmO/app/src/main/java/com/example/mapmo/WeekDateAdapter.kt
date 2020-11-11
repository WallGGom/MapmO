package com.example.mapmo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mapmo.db.NoteDataBase
import kotlinx.android.synthetic.main.week_date_item.view.*

class WeekDateAdapter(val itemClick: (ListWeekData) -> Unit) : RecyclerView.Adapter<WeekDateAdapter.WeekHolder>() {
    var listData = mutableListOf<ListWeekData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.week_date_item, parent, false)

    return WeekHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: WeekHolder, position: Int) {
        var data = listData.get(position)

        holder.bind(data)
    }

    inner class WeekHolder(itemView: View, itemClick: (ListWeekData) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bind(listdata: ListWeekData) {
            itemView.week_date.text = "${listdata.number}"
            itemView.setOnClickListener{ itemClick(listdata) }
        }
    }
}

