package com.example.sample.network.api

import com.example.sample.model.apirequest.AddAreaRequest
import com.example.sample.model.apirequest.DeleteAreaRequest
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.GetAllAreaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AreaService {
    @GET("area/getall")
    fun getAllAreas(): Call<GetAllAreaResponse>

    @POST("area/delete")
    fun deleteArea(@Body deleteAreaRequest: DeleteAreaRequest): Call<DefaultResponse>

    @POST("area/create")
    fun addArea(@Body addAreaRequest: AddAreaRequest): Call<DefaultResponse>
}