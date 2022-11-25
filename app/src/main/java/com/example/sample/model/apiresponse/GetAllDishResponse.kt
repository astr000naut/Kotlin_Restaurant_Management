package com.example.sample.model.apiresponse

import com.example.sample.model.Dish

data class GetAllDishResponse(
    val status: String,
    val message: String,
    val dishes: List<Dish>
)