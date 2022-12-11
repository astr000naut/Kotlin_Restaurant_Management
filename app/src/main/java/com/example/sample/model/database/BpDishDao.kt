package com.example.sample.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sample.model.BP_Dish

@Dao
interface BpDishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(bpDish: BP_Dish)

    @Update
    suspend fun update(bpDish: BP_Dish)

    @Delete
    suspend fun delete(bpDish: BP_Dish)

    @Query("DELETE FROM bp_dish_table WHERE id = :id")
    suspend fun deleteById(id: Int)


    @Query("UPDATE bp_dish_table SET trangthai = 'Đang làm' WHERE id = :id")
    suspend fun changestate(id: Int)

    @Query("UPDATE bp_dish_table SET ghichu = :ghichu, soluong = :soluong WHERE id = :id")
    suspend fun updateById(id: Int, soluong: Int, ghichu: String)




    @Query("SELECT * FROM bp_dish_table ORDER BY id ASC")
    fun getAll(): LiveData<List<BP_Dish>>
}