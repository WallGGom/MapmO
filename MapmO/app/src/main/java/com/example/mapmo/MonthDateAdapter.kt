package com.example.mapmo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.month_date_item.view.*

class MonthDateAdapter : RecyclerView.Adapter<MonthHolder>() {
    var listData = mutableListOf<ListMonthData>()
    var flag = false
    var year = 0
    var month = 0
    var endDateMap = mutableMapOf(
            1 to 31,
            2 to 28,
            3 to 31,
            4 to 30,
            5 to 31,
            6 to 30,
            7 to 31,
            8 to 31,
            9 to 30,
            10 to 31,
            11 to 30,
            12 to 31
    )
    var cnt = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_date_item, parent, false)
        return MonthHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {

        if (year % 4 == 0) {
            endDateMap[2] = 29
        } else {
            endDateMap[2] = 28
        }
        var data = listData.get(position)
        if ((data.number == 1) and (!flag)) {
            flag = true
            cnt = position
        } else if ((position >= endDateMap[month]!!+cnt) and (flag)) {
            flag = false
        }

        holder.bind(data, flag, cnt)
    }
}

class MonthHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(listdata: ListMonthData, flag: Boolean, cnt: Int) {
        Log.e("position", cnt.toString())
        if (!flag) {
            itemView.month_date.text = "${listdata.number}"
            //itemView.month_date.text = "없엉"
            itemView.month_date.setTextColor(-0x808080)
            itemView.month_date.isClickable = false
        } else {
            itemView.month_date.text = "${listdata.number}"
//            itemView.month_date.setTextColor(0)
            itemView.month_date.isClickable = true
        }
    }
}