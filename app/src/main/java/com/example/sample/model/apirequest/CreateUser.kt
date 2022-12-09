package com.example.sample.model.apirequest

data class CreateUser(
    val ten: String,
    val tuoi: Int,
    val sdt: String,
    val noio: String,
    val quequan: String = "",
    val email: String,
    val role: String,
    val username: String,
    val password: String
)