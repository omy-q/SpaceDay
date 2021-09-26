package com.example.spaceday.superview.view.planet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.spaceday.R
import com.example.spaceday.databinding.ActivityPlanetBottomNavigationBinding

class PlanetBottomNavigationActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlanetBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigationListener()
        initStartFragment()
    }

    private fun initStartFragment() {
        binding.bottomNavigationView.selectedItemId = R.id.action_system
    }

    private fun initNavigationListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_earth -> {
                    transferFragment(R.id.planetFragmentContainer, EarthFragment())
                    true
                }
                R.id.action_mars -> {
                    transferFragment(R.id.planetFragmentContainer, MarsFragment())
                    true
                }
                R.id.action_system -> {
                    transferFragment(R.id.planetFragmentContainer, SystemFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun transferFragment(id: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(id, fragment)
            .commit()
    }
}