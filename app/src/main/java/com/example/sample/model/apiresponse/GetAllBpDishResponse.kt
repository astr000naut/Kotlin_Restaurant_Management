package com.example.sample.model.apiresponse

import com.example.sample.model.BP_Dish

data class GetAllBpDishResponse(
    val status: String,
    val message: String,
    val bp_dishes: List<BP_Dish>
)