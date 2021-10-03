package com.example.spaceday.superview.view.planet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spaceday.databinding.FragmentEarthBinding
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.EarthViewModel

class EarthFragment : Fragment() {

    private var _binding : FragmentEarthBinding? = null
    private val binding get() = _binding!!
    private val earthViewModel : EarthViewModel by lazy {
        ViewModelProvider(this).get(EarthViewModel::class.java)
    }

    private val earthAdapter = EarthAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(earthViewModel) {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getData()
        }
    }

    private fun renderData(appState: AppState) {
        when(appState){
            is AppState.SuccessEarthData -> {
                binding.earthImageRecyclerView.layoutManager = GridLayoutManager(context, 2)
                binding.earthImageRecyclerView.adapter = earthAdapter
                binding.earthImageRecyclerView.hasFixedSize()
                earthAdapter.setData(appState.serverResponseData)
                Toast.makeText(context, "SuccessEarthData", Toast.LENGTH_SHORT).show()
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
        _binding = null
        super.onDestroyView()
    }
}