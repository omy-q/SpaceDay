package com.example.spaceday.superview.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spaceday.databinding.FavoriteImagesLayoutBinding
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.DetailsViewModel

class FavoriteImagesFragment : Fragment() {
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }
    private val adapter = FavoriteAdapter()
    private var _binding : FavoriteImagesLayoutBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = FavoriteImagesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FavoriteImagesLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getFavoriteImages()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun renderData(appState: AppState){
        when(appState){
            is AppState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            is AppState.SuccessDetails -> {
                binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
                binding.recyclerView.adapter = adapter
                adapter.setData(appState.dbResponseData)
            }
        }
    }
}