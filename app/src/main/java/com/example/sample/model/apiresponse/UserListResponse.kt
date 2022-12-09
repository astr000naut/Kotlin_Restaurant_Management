package com.example.sample.model.apiresponse

import com.example.sample.model.User

data class UserListResponse(
    val status: String,
    val message: String,
    val users: List<User>
)