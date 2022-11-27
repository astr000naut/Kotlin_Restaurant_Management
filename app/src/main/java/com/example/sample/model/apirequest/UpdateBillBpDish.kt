package com.example.sample.model.apirequest

import com.example.sample.model.BP_Dish

data class UpdateBillBpDish(
    val type: String,
    val bp_dish_id: Int,
    val soluong: Int,
    val ghichu: String
)