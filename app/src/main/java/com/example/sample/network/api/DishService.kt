package com.example.sample.network.api

import com.example.sample.model.apiresponse.GetAllDishResponse
import retrofit2.Call
import retrofit2.http.GET


interface DishService {

    @GET("dish/getall")
    fun getAllDish(): Call<GetAllDishResponse>

}