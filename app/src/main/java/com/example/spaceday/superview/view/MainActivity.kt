package com.example.spaceday.superview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spaceday.R
import com.example.spaceday.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState.let { supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .addToBackStack("")
            .commit()}
    }
}