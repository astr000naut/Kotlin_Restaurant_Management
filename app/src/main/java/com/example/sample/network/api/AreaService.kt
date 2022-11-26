package com.example.sample.network.api

import com.example.sample.model.apiresponse.GetAllAreaResponse
import retrofit2.Call
import retrofit2.http.GET

interface AreaService {
    @GET("area/getall")
    fun getAllAreas(): Call<GetAllAreaResponse>
}