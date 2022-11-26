package com.example.sample.network.api

import com.example.sample.model.apirequest.TableFilterRequest
import com.example.sample.model.apiresponse.GetAllTableResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TableService {

    @GET("table/getall")
    fun getAllTables(): Call<GetAllTableResponse>

    @POST("table/filter")
    fun filterTables(@Body tableFilterRequest: TableFilterRequest): Call<GetAllTableResponse>
}