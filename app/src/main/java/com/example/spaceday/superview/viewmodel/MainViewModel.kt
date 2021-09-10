package com.example.spaceday.superview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getData(){
        liveData.postValue(AppState.Loading)
        repository.getServerData(callback)
    }

    private val callback = object : Callback<NASAData> {
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