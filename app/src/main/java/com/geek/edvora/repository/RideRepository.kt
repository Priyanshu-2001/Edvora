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
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class RideRepository(
    private val api: RideAPI,
    private val context: Context
) {
    var rideData: List<RideDataItem>? = null
    private val rideDataDetails = MutableLiveData<List<RideDataItem>>()
    private val userDetailsData = MutableLiveData<UserData>()
    private val stateList = mutableSetOf<String>()
    private val cityList = mutableSetOf<String>()

    val rideDataList: LiveData<List<RideDataItem>>
        get() {
            return rideDataDetails
        }
    val userData: LiveData<UserData>
        get() {
            return userDetailsData
        }

    suspend fun getUserData() {
        if (NetworkUtils.isInternetAvailable(context)) {
            val result = api.getUserDetails()
            if (result.body() != null) {
                Log.e("User data ", "${result.body()}")
                userDetailsData.postValue(result.body())
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
            userDetailsData.postValue(UserData(name!!, station_code, url!!))
        }
    }

    suspend fun getRideData() {

        Log.e("TAG", "getRideData: called")
        if (rideData == null) {
            rideData = mutableListOf()
            if (NetworkUtils.isInternetAvailable(context)) {
                val result = api.getRideDetails()
                if (result.body() != null) {
                    rideData = result.body()!!
                    if (userData.value != null) {
                        performUtilityChanges((rideData as List))
                        rideData!![0].cityList = cityList
                        rideData!![0].stateList = stateList
                    } else {
                        getUserData()
                        while (userData.value == null)
                            delay(200)
                        performUtilityChanges(rideData!!)
                        rideData!![0].cityList = cityList
                        rideData!![0].stateList = stateList
                    }
                    rideDataDetails.postValue(rideData)
                }
            } else {
                //TODO offile data
            }
        } else {
            rideDataDetails.postValue(rideData)
        }
    }

    private fun getDate(dateStr: String): Date? {
        return SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.getDefault()).parse(dateStr)
    }

    private fun performUtilityChanges(rideData: List<RideDataItem>) {
        rideData.forEach {
            it.setDistance(
                MainRCVAdapter.getMinDistanceFrom(
                    it.station_path, userData.value!!.station_code
                )
            )
            it.setFormattedDate(getDate(it.date)!!)
            stateList.add(it.state)
            cityList.add(it.city)
        }
    }

}