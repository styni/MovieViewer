package com.styni.movieviewer.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
//        when(position) {
//            0 ->
//            1 ->
//            else -> "Error"
//        }

        return Fragment()
    }
}