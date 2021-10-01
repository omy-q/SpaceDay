package com.example.spaceday.superview.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App.Companion.getDB
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import com.example.spaceday.supermodel.utils.convertNasaToMonthData
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
                liveData.value = AppState.SuccessMonthData(
                    convertNasaToMonthData(response.body()!!))
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

    @SuppressLint("SimpleDateFormat")
    fun getDataOfTheDate(daysAgo: Int){
        liveData.postValue(AppState.Loading)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        calendar.add(Calendar.MONTH, -1)
        calendar.time
        calendar.add(Calendar.DATE, -daysAgo)
        val setDate = calendar.time
        repository.getDateServerData(dateFormat.format(setDate.time), callbackDate)
    }

    private val callbackDate = object : Callback<NASAData> {
        override fun onResponse(call: Call<NASAData>, response: Response<NASAData>) {
            if (response.isSuccessful && response.body() != null){
                liveData.value = AppState.Success(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()){
                    liveData.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    liveData.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<NASAData>, throwable: Throwable) {
            liveData.postValue(AppState.Error(throwable))
        }
    }
}