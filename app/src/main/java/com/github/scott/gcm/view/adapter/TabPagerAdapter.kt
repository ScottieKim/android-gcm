package com.github.scott.gcm.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabPagerAdapter(fm: FragmentManager, var list: List<Fragment>, var tabList : List<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = list[position]

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabList[position]
    }
}
