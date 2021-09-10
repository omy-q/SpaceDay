package com.example.spaceday.superview.view

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spaceday.R
import com.example.spaceday.databinding.MainFragmentBinding
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment :Fragment() {

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object{
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MainFragmentBinding.inflate(inflater, container,false)
        setBottomAppBar()
        return binding.root
    }

    private fun setBottomAppBar(){
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_load -> {
                Toast.makeText(context, "Download", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            getLiveData().observe(viewLifecycleOwner, {renderData(it)})
            getData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomSheet(binding.mainContent.bottomSheetLayout.bottomSheetContainer)
    }

    private fun initBottomSheet(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(appState : AppState){
        when(appState){
            is AppState.Success ->{
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error ->{
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            AppState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

}