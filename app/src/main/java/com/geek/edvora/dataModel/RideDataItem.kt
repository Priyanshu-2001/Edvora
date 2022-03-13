package com.geek.edvora.dataModel

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.geek.edvora.db.ListConverter

@Entity(tableName = "RideDetails")
data class RideDataItem(
    val city: String,
    val date: String,
    val destination_station_code: Int = 0,

    @PrimaryKey
    val id: Int = 0,
    val map_url: String,
    val origin_station_code: Int = 0,
    val state: String,
    val station_path: List<Int>
){
    fun getOriginString() : String{
        return origin_station_code.toString()
    }
    fun getIDString() : String{
        return id.toString()
    }

    var distanceFromUser : Int = 0
    fun setDistance(d : Int){distanceFromUser = d}
}
