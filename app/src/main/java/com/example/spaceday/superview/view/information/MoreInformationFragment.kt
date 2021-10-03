package com.example.spaceday.superview.view.information

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import coil.load
import com.example.spaceday.R
import com.example.spaceday.databinding.MoreInformationFragmentBinding
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.superview.view.main.MAIN_FRAGMENT_NAME

class MoreInformationFragment : Fragment() {
    private var nasaData : NASAData? = null
    private var _binding : MoreInformationFragmentBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val BUNDLE_EXTRA = "image"
        fun newInstance(bundle: Bundle) : MoreInformationFragment {
            val fragment = MoreInformationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MoreInformationFragmentBinding.inflate(inflater, container, false)
        nasaData = arguments?.getParcelable(BUNDLE_EXTRA) as? NASAData
        initFab()
        initView(nasaData)
        return binding.root
    }

    private fun initFab() {
        binding.moreInformationFab.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack(MAIN_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    private fun initView(nasaData: NASAData?) {
        nasaData?.let {
            if(nasaData.mediaType == "video"){
                binding.showVideoBtn.visibility = View.VISIBLE
                binding.showVideoBtn.setOnClickListener {  btn ->
                    val videoIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(nasaData.url)
                    }
                    startActivity(videoIntent)
                }
            }
            else{
                binding.showVideoBtn.visibility = View.GONE
                binding.moreInformationImage.load(nasaData.url)
            }
            with(binding){
                titleTextView.text = nasaData.title
                descriptionTextView.text = nasaData.explanation
                dateTextView.text = nasaData.date
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                    titleTextView.typeface = resources.getFont(R.font.hachi_maru_pop)
//                    descriptionTextView.typeface = resources.getFont(R.font.hachi_maru_pop)
//                    dateTextView.typeface = resources.getFont(R.font.hachi_maru_pop)
//                } else{
//                    titleTextView.typeface = Typeface.createFromAsset(requireActivity().assets,
//                        "hachi_maru_pop.ttf")
//                    descriptionTextView.typeface = Typeface.createFromAsset(requireActivity().assets,
//                        "hachi_maru_pop.ttf")
//                    dateTextView.typeface = Typeface.createFromAsset(requireActivity().assets,
//                        "hachi_maru_pop.ttf")
//                }
            }
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}