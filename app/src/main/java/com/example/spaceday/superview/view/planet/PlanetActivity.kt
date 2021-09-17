package com.example.spaceday.superview.view.planet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceday.R
import com.example.spaceday.databinding.ActivityPlanetBinding
import me.relex.circleindicator.CircleIndicator3

class PlanetActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlanetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        binding.viewPager.adapter = ViewPlanetPagerAdapter(this)
        binding.indicator.setViewPager(binding.viewPager)
        setContentView(binding.root)
    }
}