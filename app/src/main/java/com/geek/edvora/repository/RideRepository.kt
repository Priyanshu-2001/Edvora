package com.geek.edvora.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.geek.edvora.Service.RideAPI
import com.geek.edvora.adapter.MainRCVAdapter
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import com.geek.edvora.utils.NetworkUtils
import java.text.SimpleDateFormat
import java.util.*

class RideRepository(
    private val api: RideAPI,
    private val context: Context
) {
    private val RideData = MutableLiveData<List<RideDataItem>>()
    private val UserDetailsData = MutableLiveData<UserData>()

    val rideDataList: LiveData<List<RideDataItem>>
        get() {
            return RideData
        }
    val _userData: LiveData<UserData>
        get() {
            return UserDetailsData
        }

    suspend fun getUserData() {
        if (NetworkUtils.isInternetAvailable(context)) {
            val result = api.getUserDetails()
            if (result.body() != null) {
                Log.e("User data ", "${result.body()}")
                UserDetailsData.postValue(result.body())
                val pref = context.getSharedPreferences("UserDetail", MODE_PRIVATE).edit()
                pref.putString("name", result.body()!!.name)
                pref.putString("url", result.body()!!.url)
                pref.putInt("station_code", result.body()!!.station_code)
                pref.apply()
            }
        } else {
            val pref = context.getSharedPreferences("UserDetail", MODE_PRIVATE)
            val name = pref.getString("name", "")
            val station_code = pref.getInt("station_code", 0)
            val url = pref.getString("url", "")
            UserDetailsData.postValue(UserData(name!!, station_code, url!!))
        }
    }

    suspend fun getRideData() {
        if (NetworkUtils.isInternetAvailable(context)) {
            lateinit var rideData: List<RideDataItem>
            lateinit var finalRideData: List<RideDataItem>
            val result = api.getRideDetails()
            if (result.body() != null) {
                rideData = result.body()!!
                rideData.forEach {
                    it.setDistance(MainRCVAdapter.getMinDistanceFrom(
                        it.station_path
                        ,_userData.value!!.station_code)
                    )
                    it.setFormattedDate(getDate(it.date)!!)
                }
                RideData.postValue(rideData)
            }
        } else {
//            lateinit var rideData: List<RideDataItem>
//            if (tab == -1) {
//                rideData = RideDB.dao().getTodayRides()
//                Log.e("TAG", "getRideData: ${rideData[0].distanceFromUser}")
//
//                rideData.sortedBy {
//                    it.distanceFromUser
//                }
//            }
////            if (tab == 0)
////                rideData = RideDB.dao().getFutureRides(currentTime)
////            if (tab == 1)
////                rideData = RideDB.dao().getPastRides(currentTime)
//            RideData.postValue(rideData)
        }

    }

    fun getDate(dateStr: String): Date? {
        return SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.getDefault()).parse(dateStr)
    }
}