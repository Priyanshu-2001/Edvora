package com.geek.edvora.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.geek.edvora.dataModel.RideData
import com.geek.edvora.dataModel.RideDataItem
@Dao
interface DAO {

    @Query(" SELECT * FROM RideDetails WHERE date < :currentDate") //0 -> present
    fun getPastRides(currentDate : Long) : List<RideDataItem>                // 1 -> future , -1 -> past

//    @Query("SELECT * FROM RideDetails WHERE date == :currentDate")
    @Query("SELECT * FROM RideDetails ")
    fun getTodayRides() : List<RideDataItem>

    @Query("SELECT * FROM RideDetails WHERE date == :currentDate")
    fun getNearestRides(currentDate : Long) : List<RideDataItem>

    @Query("SELECT * FROM RideDetails WHERE date > :currentDate")
    fun getFutureRides(currentDate : Long) : List<RideDataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRide(data : List<RideDataItem>)
}