package com.example.spaceday.superview.view.planet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceday.databinding.ActivityPlanetBinding

class PlanetActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlanetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = ViewPlanetPagerAdapter(this)
    }
}