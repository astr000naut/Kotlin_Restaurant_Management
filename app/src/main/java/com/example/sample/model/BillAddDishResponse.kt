package com.example.sample.model

data class BillAddDishResponse(
    val status: String,
    val message: String,
    val hoadonmonId: List<Int>
)