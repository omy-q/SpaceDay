package com.example.spaceday.supermodel.repository

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.spaceday.supermodel.local.repository.CallbackDB
import com.example.spaceday.supermodel.local.repository.RepositoryDB
import com.example.spaceday.supermodel.remote.NASAData
import com.example.spaceday.supermodel.remote.RemoteDataSource
import com.example.spaceday.supermodel.utils.convertToEntity
import com.example.spaceday.supermodel.utils.convertTypes
import retrofit2.Callback

class RepositoryImpl(private val localDataSource : RepositoryDB) :Repository {
    private val remoteDataSource = RemoteDataSource()
    private val handlerMain = Handler(Looper.getMainLooper())

    override fun getServerData(callback: Callback<NASAData>) {
        remoteDataSource.loadDayImage(callback)
    }

    override fun getDateServerData(date : String, callback: Callback<NASAData>) {
        Log.i("time", "$date")
        remoteDataSource.loadDateImage(date, callback)
    }

    override fun getFavoriteImages(callback : CallbackDB) {
        Thread{
            val data = convertTypes(localDataSource.getFavorite().all())
            handlerMain.post{callback.onResponse(data)}
        }.start()
    }

    override fun deleteFavoriteImage(image : NASAData) {
        Thread{
            localDataSource.getFavorite().delete(convertToEntity(image))
        }.start()
    }

    override fun saveFavoriteImage(image : NASAData) {
        Thread{
            localDataSource.getFavorite().insert(convertToEntity(image))
        }.start()
    }

}