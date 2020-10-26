package com.example.frontend

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {

    var fragmentList = listOf<Fragment>()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int) : CharSequence?{
        return when (position) {
            0 -> "일"
            1 -> "주"
            else -> "월"

        }
    }
}