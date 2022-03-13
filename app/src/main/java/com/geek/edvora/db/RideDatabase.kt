package com.geek.edvora.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.geek.edvora.dataModel.RideDataItem

@Database(entities = [RideDataItem::class] , version = 2)
@TypeConverters(ListConverter::class)
abstract class RideDatabase : RoomDatabase() {

    abstract fun dao() : DAO

    companion object{
        @Volatile
        private var INSTANCE : RideDatabase? = null
        fun getDatabase(context : Context) : RideDatabase{
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        RideDatabase::class.java,
                        "RideDetails")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}