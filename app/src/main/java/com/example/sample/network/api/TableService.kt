package com.example.sample.network.api

import com.example.sample.model.apiresponse.GetAllTableResponse
import retrofit2.Call
import retrofit2.http.GET

interface TableService {

    @GET("table/getall")
    fun getAllTables(): Call<GetAllTableResponse>

}