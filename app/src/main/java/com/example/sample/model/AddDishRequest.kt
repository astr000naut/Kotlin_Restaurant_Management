package com.example.sample.model

data class AddDishRequest(
    val bill_id: Int,
    val dishes: List<Dish>
)