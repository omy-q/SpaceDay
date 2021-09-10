package com.example.spaceday.superview.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spaceday.databinding.MainFragmentBinding
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MainViewModel

class MainFragment :Fragment() {

    private var _binding : MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    companion object{
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MainFragmentBinding.inflate(inflater, container,false)
        return binding.root
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