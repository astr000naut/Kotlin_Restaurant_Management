package com.example.sample.network

import com.example.sample.model.GetAllTableResponse
import com.example.sample.model.LoginRequest
import com.example.sample.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TableService {

    @GET("table/getall")
    fun getAllTables(): Call<GetAllTableResponse>

}