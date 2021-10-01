package com.example.spaceday.superview.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App.Companion.getDB
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

const val MONTH_IMAGE_FRAGMENT_NAME = "MonthImageFragment"
class MonthImageViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(getDB()))) : ViewModel(){

    fun getLiveData() = liveData

    @SuppressLint("SimpleDateFormat")
    fun getData(){
        liveData.value = AppState.Loading
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val endDate = dateFormat.format(calendar.time)
        calendar.add(Calendar.MONTH, -1)
        val startDate = dateFormat.format(calendar.time)
        repository.getMonthServerData(startDate, endDate, callback)
    }

    private val callback = object: Callback<ArrayList<NASAData>> {
        override fun onResponse(call: Call<ArrayList<NASAData>>,
                                response: Response<ArrayList<NASAData>>){
            if (response.isSuccessful && response.body() != null){
                liveData.value = AppState.SuccessMonthData(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()){
                    liveData.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    liveData.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<NASAData>>, throwable: Throwable){
            liveData.value = AppState.Error(throwable)
        }
    }
}