package com.example.sample.network.api

import com.example.sample.model.apirequest.LoginRequest
import com.example.sample.model.apiresponse.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @Headers("Accept: application/json")
    @POST("user/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}