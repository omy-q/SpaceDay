package com.example.spaceday.superview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.EarthData
import com.example.spaceday.supermodel.remote.MarsData
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(App.getDB()))
) : ViewModel() {

    fun getLiveData() = liveData

    fun getData(date: String){
        liveData.postValue(AppState.Loading)
        repository.getMarsServerData(date, callback)
    }

    private val callback = object : Callback<MarsData> {

        override fun onResponse(call: Call<MarsData>, response: Response<MarsData>) {
            if (response.isSuccessful && response.body() != null){
                liveData.value = AppState.SuccessMarsData(response.body()!!)
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()){
                    liveData.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    liveData.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<MarsData>, throwable: Throwable) {
            liveData.postValue(AppState.Error(throwable))
        }
    }
}