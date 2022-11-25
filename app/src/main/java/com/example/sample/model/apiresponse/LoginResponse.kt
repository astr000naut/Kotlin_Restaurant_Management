package com.example.sample.model.apiresponse

import com.example.sample.model.User

data class LoginResponse(
    val status: String,
    val message: String,
    val user: User
)