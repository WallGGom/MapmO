package com.example.mapmo

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_week_memo.*

class FragmentWeekMemo : Fragment() {
    var helper:MemoRoomHelper? = null
    private var weekList = mutableListOf<MemoRoom>()
    lateinit var weekAdapter: MemoRecyclerAdapter
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }

    companion object {
        fun newInstance(): FragmentWeekMemo {
            return FragmentWeekMemo()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val ctx1 = context ?: return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_week_memo, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //        val ctx1 = context ?: return
        val ctx1 = requireContext()

        helper = Room.databaseBuilder(ctx1, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
        weekList.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())
        Log.e("list", weekList.toString())

        val adapter = MemoRecyclerAdapter(weekList, 2)
        adapter.helper = helper
        adapter.notifyDataSetChanged()

        week_rec.adapter = adapter
        week_rec.layoutManager = linearLayoutManager
        week_rec.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}