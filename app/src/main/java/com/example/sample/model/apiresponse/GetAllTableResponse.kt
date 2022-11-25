package com.example.sample.model.apiresponse

import com.example.sample.model.Table

data class GetAllTableResponse(
    val status: String,
    val message: String,
    val tables: List<Table>
)