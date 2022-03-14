package com.geek.edvora.utils

import android.app.Application
import com.geek.edvora.Service.RideAPI
import com.geek.edvora.repository.RideRepository

class MyApplication : Application() {
    lateinit var studentRepo: RideRepository
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val service = RetrofitHelper().getInstance().create(RideAPI::class.java)
        studentRepo = RideRepository(service, applicationContext)
    }
}