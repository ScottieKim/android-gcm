package com.github.scott.gcm.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager, var list: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int {
        return list.size
    }
}