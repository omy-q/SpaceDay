package com.example.spaceday.superview.view

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.spaceday.R
import com.example.spaceday.databinding.MainFragmentBinding
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.superview.view.favorite.FavoriteImagesFragment
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MainViewModel
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment :Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var currentImage: NASAData? = null
    private var isPush = false

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        setBottomAppBar()
        return binding.root
    }

    private fun setBottomAppBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        initFab()
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            if (isPush) {
                isPush = false
                binding.fab.setImageDrawable(
                    ContextCompat
                        .getDrawable(requireContext(), R.drawable.ic_favorite)
                )
                currentImage?.let { image -> viewModel.deleteImage(image) }
            } else {
                isPush = true
                binding.fab.setImageDrawable(
                    ContextCompat
                        .getDrawable(requireContext(), R.drawable.ic_favorite_pushed)
                )
                currentImage?.let { image -> viewModel.saveImage(image) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuBottomBarDownload -> {
                Toast.makeText(context, "Download", Toast.LENGTH_SHORT).show()
            }
            R.id.menuBottomBarSettings -> {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, FavoriteImagesFragment.newInstance())
                    .addToBackStack("")
                    .commit()
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
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet(binding.mainContent.bottomSheetLayout.bottomSheetContainer)
    }

    private fun initInputLayoutListener() {
        binding.mainContent.textInputLayout.setEndIconOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(
                    "https://en.wikipedia.org/wiki/" +
                            "${binding.mainContent.inputEditText.text.toString()}"
                )
            }
            startActivity(intent)
        }
    }

    private fun initBottomSheet(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                currentImage = appState.serverResponseData
                binding.mainContent.imageView.load(appState.serverResponseData.url) {
                    placeholder(R.drawable.progress_animation)
                    error(R.drawable.ic_error_load)
                }
                binding.mainContent.bottomSheetLayout
                    .bottomSheetDescriptionHeader.text = appState.serverResponseData.title
                binding.mainContent.bottomSheetLayout
                    .bottomSheetDescription.text = appState.serverResponseData.explanation
            }
            is AppState.Error -> {
                binding.mainContent.imageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_error_load)
                }
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            AppState.Loading -> {
                binding.mainContent.imageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_error_load)
                }
            }
        }
    }
}

