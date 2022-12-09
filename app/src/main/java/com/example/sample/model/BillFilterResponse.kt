package com.example.sample.model

data class BillFilterResponse(
    val status: String,
    val message: String,
    val bills: List<Bill>
)