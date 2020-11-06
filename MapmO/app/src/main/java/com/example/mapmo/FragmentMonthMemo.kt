package com.example.mapmo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_month_memo.*


class FragmentMonthMemo : Fragment() {

    var helper:MemoRoomHelper? = null
    private var monthList = mutableListOf<MemoRoom>()
    lateinit var monthAdapter: MemoRecyclerAdapter
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }

    companion object {
        fun newInstance(): FragmentMonthMemo {
            return FragmentMonthMemo()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_month_memo, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //        val ctx2 = context ?: return
        val ctx2 = requireContext()

        helper = Room.databaseBuilder(ctx2, MemoRoomHelper::class.java, "room_memo").allowMainThreadQueries().build()
        monthList.addAll(helper?.memoRoomDao()?.getAll() ?: mutableListOf())
        Log.e("list", monthList.toString())

        val adapter = MemoRecyclerAdapter(monthList, 3)
        adapter.helper = helper
        adapter.notifyDataSetChanged()

        month_rec.adapter = adapter
        month_rec.layoutManager = linearLayoutManager
        month_rec.setHasFixedSize(true)
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