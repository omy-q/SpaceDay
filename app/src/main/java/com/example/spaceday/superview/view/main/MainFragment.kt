package com.example.spaceday.superview.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.example.spaceday.R
import com.example.spaceday.databinding.MainFragmentBinding
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.superview.view.MainActivity
import com.example.spaceday.superview.view.favorite.FavoriteImagesFragment
import com.example.spaceday.superview.view.information.MoreInformationFragment
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip

const val MAIN_FRAGMENT_NAME = "MainFragment"

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private var wikipediaIsVisible = false
    private var isExpanded = false

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
        initWikiImageViewListener()
        initImageViewListener()
        return binding.root
    }

    private fun initImageViewListener() {
        binding.mainContent.imageView.setOnClickListener {
            isExpanded = !isExpanded
            val set = TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
            TransitionManager.beginDelayedTransition(binding.mainContent.imageContainer, set)
            binding.mainContent.imageView.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP
                else ImageView.ScaleType.CENTER_INSIDE
        }
    }

    private fun initWikiImageViewListener() {
        binding.mainContent.wikiImageView.setOnClickListener {
            TransitionManager.beginDelayedTransition(
                binding.mainContent.textInputLayoutContainer,
                Slide(Gravity.START)
            )
            wikipediaIsVisible = !wikipediaIsVisible
            binding.mainContent.textInputLayout.visibility =
                if (wikipediaIsVisible) View.VISIBLE else View.INVISIBLE
            binding.mainContent.wikiImageView.visibility = View.GONE
        }
    }

    private fun setBottomAppBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
        initFab()
    }

    private fun initFab() {
        binding.fab.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, MoreInformationFragment.newInstance(Bundle().apply {
                    putParcelable(MoreInformationFragment.BUNDLE_EXTRA, currentImage)
                }))
                .addToBackStack(MAIN_FRAGMENT_NAME)
                .commit()
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
        initInputLayoutListener()
        initChipChangeListener()
    }

    private fun initChipChangeListener() {
        binding.mainContent.chipsLayout
            .chipsGroup.setOnCheckedChangeListener { childGroup, id ->
                when (id) {
                    R.id.firstChip -> {
                        changeChipSize(binding.mainContent.chipsLayout.firstChip)
                        Toast.makeText(context, "Click 0", Toast.LENGTH_SHORT).show()
                        viewModel.getDataOfTheDate(0)
                    }
                    R.id.secondChip -> {
                        changeChipSize(binding.mainContent.chipsLayout.secondChip)
                        Toast.makeText(context, "Click 1", Toast.LENGTH_SHORT).show()
                        viewModel.getDataOfTheDate(1)
                    }
                    R.id.thirdChip -> {
                        changeChipSize(binding.mainContent.chipsLayout.thirdChip)
                        Toast.makeText(context, "Click 2", Toast.LENGTH_SHORT).show()
                        viewModel.getDataOfTheDate(2)
                    }
                }
            }
    }

    private fun changeChipSize(clickedChip: Chip) {
        val set = TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeImageTransform())
        TransitionManager.beginDelayedTransition(binding.mainContent.chipsLayout.chipsGroup, set)

        binding.mainContent.chipsLayout.firstChip.layoutParams
            .height = resources.getDimensionPixelSize(R.dimen.default_height_chip)
        binding.mainContent.chipsLayout.secondChip.layoutParams
            .height = resources.getDimensionPixelSize(R.dimen.default_height_chip)
        binding.mainContent.chipsLayout.thirdChip.layoutParams
            .height = resources.getDimensionPixelSize(R.dimen.default_height_chip)

        binding.mainContent.chipsLayout.firstChip.layoutParams
            .width = resources.getDimensionPixelSize(R.dimen.default_width_chip)
        binding.mainContent.chipsLayout.secondChip.layoutParams
            .width = resources.getDimensionPixelSize(R.dimen.default_width_chip)
        binding.mainContent.chipsLayout.thirdChip.layoutParams
            .width = resources.getDimensionPixelSize(R.dimen.default_width_chip)

        val params = clickedChip.layoutParams
        params.width = resources.getDimensionPixelSize(R.dimen.selected_width_chip)
        params.height = resources.getDimensionPixelSize(R.dimen.selected_height_chip)
        clickedChip.layoutParams = params

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
                if (appState.serverResponseData.mediaType == "video") {
                    binding.mainContent.imageView.visibility = View.GONE
                    binding.mainContent.videoView.apply {
                        visibility = View.VISIBLE
                        setMediaController(MediaController(context))
                        setVideoURI(Uri.parse(appState.serverResponseData.url))
                    }
                } else {
                    binding.mainContent.videoView.visibility = View.GONE
                    binding.mainContent.imageView.apply {
                        visibility = View.VISIBLE
                        load(appState.serverResponseData.url) {
                            placeholder(R.drawable.progress_animation)
                            error(R.drawable.ic_error_load)
                        }
                    }
                }
                binding.mainContent.bottomSheetLayout
                    .bottomSheetDescriptionHeader.text = appState.serverResponseData.title
                binding.mainContent.bottomSheetLayout
                    .bottomSheetDescription.text = appState.serverResponseData.explanation
            }
            is AppState.Error -> {
                binding.mainContent.videoView.visibility = View.GONE
                binding.mainContent.imageView.visibility = View.VISIBLE
                binding.mainContent.imageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_error_load)
                }
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            AppState.Loading -> {
                binding.mainContent.videoView.visibility = View.GONE
                binding.mainContent.imageView.visibility = View.VISIBLE
                binding.mainContent.imageView.load(R.drawable.progress_animation)
            }
        }
    }
}

