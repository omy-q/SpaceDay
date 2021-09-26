package com.example.spaceday.superview.view.planet

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


private const val EARTH = 0
private const val MARS = 1

class ViewPlanetPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment())

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragments[EARTH]
            1 -> fragments[MARS]
            else -> fragments[EARTH]
        }
    }
}