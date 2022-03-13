package com.geek.edvora.Service

import com.geek.edvora.dataModel.RideData
import com.geek.edvora.dataModel.RideDataItem
import com.geek.edvora.dataModel.UserData
import retrofit2.Response
import retrofit2.http.GET

interface RideAPI {

    @GET("/rides")
    suspend fun getRideDetails() : Response<ArrayList<RideDataItem>>
    @GET("/user")
    suspend fun getUserDetails() : Response<UserData>
}