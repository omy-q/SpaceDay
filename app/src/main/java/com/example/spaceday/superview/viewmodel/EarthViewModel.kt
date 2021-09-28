package com.example.spaceday.superview.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.EarthDataDTO
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import com.example.spaceday.supermodel.utils.convertEarthDTOtoData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(App.getDB()))
) : ViewModel() {

    fun getLiveData() = liveData

    fun getData(){
        liveData.postValue(AppState.Loading)
        repository.getEarthServerData(callback)
    }

    private val callback = object : Callback<ArrayList<EarthDataDTO>> {

        override fun onResponse(call: Call<ArrayList<EarthDataDTO>>,
                                response: Response<ArrayList<EarthDataDTO>>) {
            if (response.isSuccessful && response.body() != null){
                Log.i("onResponse", "Successful")
                liveData.value = AppState.SuccessEarthData(convertEarthDTOtoData(response.body()!!))
            } else {
                Log.i("onResponse", "Error")
                val message = response.message()
                if (message.isNullOrEmpty()){
                    liveData.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    Log.i("onResponse", "Error $message")
                    liveData.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<ArrayList<EarthDataDTO>>, throwable: Throwable) {
            Log.i("onResponse", "Error $throwable")
            liveData.postValue(AppState.Error(throwable))
        }
    }
}