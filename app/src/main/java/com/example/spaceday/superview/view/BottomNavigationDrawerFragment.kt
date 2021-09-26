package com.example.spaceday.superview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.spaceday.R
import com.example.spaceday.databinding.BottomNavigationLayoutBinding
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
                }
                R.id.menuNavigationSecondScreen -> {
                    Toast.makeText(context, "2", Toast.LENGTH_SHORT).show()
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


