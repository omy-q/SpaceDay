package com.example.spaceday.superview.view.month.image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spaceday.databinding.MonthImageFragmentBinding
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.superview.viewmodel.AppState
import com.example.spaceday.superview.viewmodel.MonthImageViewModel

class MonthImageFragment : Fragment() {

    private var _binding: MonthImageFragmentBinding? = null
    private val binding get() = _binding!!

    private val monthImageViewModel : MonthImageViewModel by lazy {
        ViewModelProvider(this). get(MonthImageViewModel::class.java)
    }

    private val adapter = MonthImageAdapter(object : OnItemShowVideoBtnClickListener{
        override fun onItemShowVideoBtnClick(videoData: NASAData) {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse(videoData.url)
            }
            startActivity(intent)
        }
    })

    companion object {
        fun newInstance() = MonthImageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MonthImageFragmentBinding.inflate(inflater, container, false)
//        initFab()
        return binding.root
    }

//    private fun initFab() {
//        binding.monthImageFab.setOnClickListener{
//            data
//            adapter.appendItem()
//        }
//    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(monthImageViewModel) {
            getLiveData().observe(viewLifecycleOwner,{ renderData(it)})
            getData()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessMonthData -> {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                val header = NASAData("", "", "",
                    "", "", "", "")
                appState.serverResponseData.add(0, Pair(header, false))
                binding.monthImageRecyclerView.adapter = adapter
                binding.monthImageRecyclerView
                    .addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
                appState.serverResponseData.add(0, header)
                adapter.setData(appState.serverResponseData)
            }
            is AppState.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
