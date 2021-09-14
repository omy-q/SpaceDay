package com.example.spaceday.superview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spaceday.app.App
import com.example.spaceday.app.App.Companion.getDB
import com.example.spaceday.supermodel.local.repository.CallbackDB
import com.example.spaceday.supermodel.local.repository.RepositoryDB
import com.example.spaceday.supermodel.local.repository.RepositoryDBImpl
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.repository.Repository
import com.example.spaceday.supermodel.repository.RepositoryImpl

class DetailsViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: Repository = RepositoryImpl(RepositoryDBImpl(getDB()))
) : ViewModel() {

    fun getLiveData() = liveData

    fun getFavoriteImages(){
        repository.getFavoriteImages(callback)
    }

    private val callback = object : CallbackDB{
        override fun onResponse(result: List<NASAData>) {
            if(!result.isNullOrEmpty()){
                liveData.postValue(AppState.SuccessDetails(result))
            }else {
                liveData.postValue(AppState.Error(Throwable()))
            }
        }
    }
}