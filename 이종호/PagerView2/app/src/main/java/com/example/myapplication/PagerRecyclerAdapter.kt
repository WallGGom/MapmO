package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PagerRecyclerAdapter(private val bgColors: List<Memo>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pager, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind1(bgColors[position])
        holder.bind2(bgColors[position].bgcolor, position)
    }

    override fun getItemCount(): Int = bgColors.size
}