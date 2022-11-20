package com.example.sample.model

data class Dish(
    val id: Int,
    val ten: String,
    val gia: Int,
    var soluong: Int = 0,
    var ghichu: String
) {}