package com.example.sample.model

data class LoginResponse(
    val status: String,
    val message: String,
    val user: User
)