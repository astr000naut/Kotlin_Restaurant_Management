package com.example.sample.model

data class BillResponse(
    val status: String,
    val message: String,
    val bill: Bill
)