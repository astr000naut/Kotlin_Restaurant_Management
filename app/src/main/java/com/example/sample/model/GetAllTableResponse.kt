package com.example.sample.model

data class GetAllTableResponse(
    val status: String,
    val message: String,
    val tables: List<Table>
)