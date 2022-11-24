package com.example.sample.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sample.model.BP_Dish

@Database(entities = [BP_Dish::class], version = 1, exportSchema = false)
abstract class BpDishDatabase : RoomDatabase() {
    abstract val bpDishDao: BpDishDao

    companion object {
        @Volatile
        private var INSTANCE: BpDishDatabase? = null

        fun getInstance(context: Context): BpDishDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BpDishDatabase::class.java,
                        "bp_dish_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}