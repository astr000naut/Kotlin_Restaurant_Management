package com.example.sample.model.apiresponse

import com.example.sample.model.Area

data class GetAllAreaResponse(
    val status: String,
    val message: String,
    val areas: List<Area>
)