package com.example.sample.model

data class GetAllBpDishResponse(
    val status: String,
    val message: String,
    val bp_dishes: List<BP_Dish>
)