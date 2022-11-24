package com.example.sample.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sample.model.BP_Dish

@Dao
interface BpDishDao {
    @Insert
    suspend fun insert(bpDish: BP_Dish)

    @Update
    suspend fun update(bpDish: BP_Dish)

    @Delete
    suspend fun delete(bpDish: BP_Dish)

    @Query("SELECT * FROM bp_dish_table ORDER BY dbid ASC")
    fun getAll(): LiveData<List<BP_Dish>>
}