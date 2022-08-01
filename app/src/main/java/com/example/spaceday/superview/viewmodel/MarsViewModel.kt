package com.example.spaceday.superview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.MarsDataDTO
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl
import com.example.spaceday.supermodel.utils.convertMarsDTOtoPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(App.getDB()))
) : ViewModel() {

    fun getLiveData() = liveData

    fun getData(){
        liveData.postValue(AppState.Loading)
        repository.getMarsServerData(callback)
    }

    fun getDataByDate(date : String){
        liveData.postValue(AppState.Loading)
        repository.getMarsServerDataByDate(date, callback)
    }

    private val callback = object : Callback<MarsDataDTO> {

        override fun onResponse(call: Call<MarsDataDTO>, response: Response<MarsDataDTO>) {
            if (response.isSuccessful && response.body() != null){
                liveData.value = AppState.SuccessMarsData(convertMarsDTOtoPhoto(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()){
                    liveData.value = AppState.Error(Throwable("Unidentified Error"))
                } else {
                    liveData.value = AppState.Error(Throwable(message))
                }
            }
        }

        override fun onFailure(call: Call<MarsDataDTO>, throwable: Throwable) {
            liveData.postValue(AppState.Error(throwable))
        }
    }
}