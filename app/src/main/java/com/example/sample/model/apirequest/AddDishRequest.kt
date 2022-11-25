package com.example.sample.model.apirequest

import com.example.sample.model.Dish

data class AddDishRequest(
    val bill_id: Int,
    val dishes: List<Dish>
)