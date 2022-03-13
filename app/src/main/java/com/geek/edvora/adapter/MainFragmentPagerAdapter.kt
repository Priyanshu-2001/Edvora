package com.geek.edvora.adapter

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.geek.edvora.ui.HomeFragment

class MainFragmentPagerAdapter(private val fm : FragmentManager, private val behavior: Int) : FragmentPagerAdapter(fm,behavior) {
    override fun getCount(): Int {
        return behavior
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance(-1)
            1 -> HomeFragment.newInstance(0)
            else -> HomeFragment.newInstance(1)
        }
    }
}