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

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(getDB()))
) : ViewModel() {

    fun getLiveData() = liveData

    fun getData(){
        liveData.postValue(AppState.Loading)
        repository.getServerData(callback)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDataOfTheDate(dayId : Int){
        liveData.postValue(AppState.Loading)
        val date = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        when(dayId){
            0 -> {
                repository.getDateServerData(dateFormat.format(date.time), callback)
            }
            1 -> {
                date.add(Calendar.DATE, -1)
                repository.getDateServerData(dateFormat.format(date.time), callback)
            }
            2 -> {
                date.add(Calendar.DATE, -2)
                repository.getDateServerData(dateFormat.format(date.time), callback)
            }
        }
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
    fun saveImage(image : NASAData){
        repository.saveFavoriteImage(image)
    }

    fun deleteImage(image : NASAData){
        repository.deleteFavoriteImage(image)
    }
}