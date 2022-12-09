package com.example.sample.model.apirequest

import com.example.sample.model.Dish

data class CreateDishRequest(
    val ten: String,
    val gia: Int
)