package com.example.spaceday.superview.viewmodel

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

class MonthImageViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(getDB()))) : ViewModel(){

    fun getLiveData() = liveData

    fun getData(){
        liveData.value = AppState.Loading
        val startDate = "2021-09-01"
        val endDate = "2021-09-30"
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