package com.example.mapmo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.month_date_item.view.*

class MonthDateAdapter : RecyclerView.Adapter<MonthHolder>() {
    var listData = mutableListOf<ListMonthData>()
    var flag = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_date_item, parent, false)
        return MonthHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {
        var data = listData.get(position)
        if ((data.number == 1) and (!flag)) {
            flag = true
        } else if ((data.number == 1) and (flag)) {
            flag = false
        }

        holder.bind(data, flag)
    }
}

class MonthHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(listdata: ListMonthData, flag: Boolean) {
        if (!flag) {
//            itemView.month_date.text = "${listdata.number}"
            itemView.month_date.text = "없엉"
//            itemView.month_date.setTextColor(255)
            itemView.month_date.isClickable = false
        } else {
            itemView.month_date.text = "${listdata.number}"
//            itemView.month_date.setTextColor(0)
            itemView.month_date.isClickable = true
        }
    }
}