package com.example.sample.model

data class Dish(
    val id: Int,
    var ban: String,
    val ten: String,
    val gia: Int,
    var soluong: Int = 0,
    var ghichu: String = "",
    var billId: Int,
    var trangthai: String = ""
) {}