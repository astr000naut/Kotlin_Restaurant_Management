package com.example.sample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bp_dish_table")
data class BP_Dish(
    @PrimaryKey
    val id: Int,
    var ban: Int,
    val ten: String,
    val gia: Int,
    var soluong: Int = 0,
    @ColumnInfo(name = "ghichu", defaultValue = "")
    var ghichu: String?,
    @ColumnInfo(name = "trangthai", defaultValue = "")
    var trangthai: String?,
    var billId: Int
) {}