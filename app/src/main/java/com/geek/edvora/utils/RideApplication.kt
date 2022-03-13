package com.geek.edvora.utils

import android.app.Application
import com.geek.edvora.Service.RideAPI
import com.geek.edvora.db.RideDatabase
import com.geek.edvora.repository.RideRepository

class RideApplication : Application() {
    lateinit var RideRepo: RideRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val service = RetrofitHelper().getInstance().create(RideAPI::class.java)
        val db = RideDatabase.getDatabase(applicationContext)
        RideRepo = RideRepository(service,db,applicationContext)
    }
}