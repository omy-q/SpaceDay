package com.example.spaceday.superview.view.planet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceday.R
import com.example.spaceday.databinding.ActivityPlanetBinding
import com.google.android.material.tabs.TabLayoutMediator
import me.relex.circleindicator.CircleIndicator3

class PlanetActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlanetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPlanetPagerAdapter(this)
        binding.indicator.setViewPager(binding.viewPager)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()

        binding.tabLayout.getTabAt(0)?.customView =
            layoutInflater.inflate(R.layout.tablayout_earth,null)
        binding.tabLayout.getTabAt(1)?.customView =
            layoutInflater.inflate(R.layout.tablayout_mars,null)
    }
}