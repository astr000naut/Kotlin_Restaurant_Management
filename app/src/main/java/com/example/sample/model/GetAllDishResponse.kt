package com.example.sample.model

data class GetAllDishResponse(
    val status: String,
    val message: String,
    val dishes: List<Dish>
)