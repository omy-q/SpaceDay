package com.example.spaceday.app

import android.app.Application
import androidx.room.Room
import com.example.spaceday.supermodel.local.DataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: DataBase? = null
        private const val DB_NAME = "Images.db"

        fun getDB(): DataBase {
            if (db == null) {
                synchronized(DataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            DataBase::class.java,
                            DB_NAME)
                            .build()
                    }
                }
            }
            return db!!
        }
    }
}
