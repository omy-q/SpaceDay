package com.example.spaceday.superview.view.planet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spaceday.databinding.FragmentMarsBinding
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MarsViewModel

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!
    private val marsViewModel: MarsViewModel by lazy {
        ViewModelProvider(this).get(MarsViewModel::class.java)
    }

    private val marsAdapter = MarsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDateBtnListener()
    }

    private fun initDateBtnListener() {
        binding.dateBtn.setOnClickListener {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        marsViewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        marsViewModel.getData()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessMarsData -> {
                binding.marsImageRecyclerView.layoutManager = GridLayoutManager(context, 2)
                binding.marsImageRecyclerView.adapter = marsAdapter
                binding.marsImageRecyclerView.hasFixedSize()
                marsAdapter.setData(appState.serverResponseData)
                binding.dateTextView.text = appState.serverResponseData[0].date
                Toast.makeText(context, "SuccessMarsData", Toast.LENGTH_SHORT).show()
            }
            is AppState.Error -> {
                Toast.makeText(context, "${appState.error}", Toast.LENGTH_SHORT).show()
            }
            AppState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}