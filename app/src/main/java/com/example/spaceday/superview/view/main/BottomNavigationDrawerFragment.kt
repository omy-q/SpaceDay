package com.example.spaceday.superview.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spaceday.R
import com.example.spaceday.databinding.BottomNavigationLayoutBinding
import com.example.spaceday.superview.view.month.image.MonthImageFragment
import com.example.spaceday.superview.view.planet.PlanetActivity
import com.example.spaceday.superview.view.planet.PlanetBottomNavigationActivity
import com.example.spaceday.superview.viewmodel.MONTH_IMAGE_FRAGMENT_NAME
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomNavigationDrawerFragment : BottomSheetDialogFragment(){
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = BottomNavigationDrawerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuNavigationFirstScreen -> {
                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, PlanetActivity::class.java))
                }
                R.id.menuNavigationSecondScreen -> {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, PlanetBottomNavigationActivity::class.java))
                }
                R.id.menuNavigationThirdScreen -> {
                    Toast.makeText(context, "3", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, MonthImageFragment.newInstance())
                        .addToBackStack(MONTH_IMAGE_FRAGMENT_NAME)
                        .commit()
                }
            }
            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}


