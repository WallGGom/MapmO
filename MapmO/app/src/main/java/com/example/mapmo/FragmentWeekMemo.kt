package com.example.mapmo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapmo.db.NoteDataBase
import com.example.mapmo.models.NoteModel
import kotlinx.android.synthetic.main.fragment_week_memo.*

class FragmentWeekMemo : Fragment() {
    var helper: NoteDataBase? = null
    private var weekList = mutableListOf<NoteModel>()
    lateinit var weekAdapter: MemoRecyclerAdapter
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }

    private var mNoteDataBase : NoteDataBase? = null


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        val bundle: Bundle? = arguments
//
//        if (bundle != null) {
//            str = bundle.getString("key")
//        }

        return inflater.inflate(R.layout.fragment_week_memo, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //        val ctx1 = context ?: return
        val ctx1 = requireContext()

        var mNoteList: MutableList<NoteModel>? = null




        mNoteDataBase = NoteDataBase.getInstance(ctx1)

        val addRunnable = Runnable {
            try {
                mNoteList = mNoteDataBase?.noteItemAndNotesModel()?.getAll()
                Log.e("temp1", mNoteList.toString())
                weekAdapter = MemoRecyclerAdapter(mNoteList!!, 2)
                weekAdapter.notifyDataSetChanged()
                week_rec.adapter = weekAdapter
                week_rec.layoutManager = linearLayoutManager
                week_rec.setHasFixedSize(true)
            } catch (e: Exception) {
                Log.d("tag", "Error - $e")
            }
        }
        val addThread = Thread(addRunnable)
        addThread.start()

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
        NoteDataBase.destroyInstance()
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}