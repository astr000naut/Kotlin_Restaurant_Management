package com.example.sample.network

import com.example.sample.model.GetAllDishResponse
import retrofit2.Call
import retrofit2.http.GET


interface DishService {

    @GET("dish/getall")
    fun getAllDish(): Call<GetAllDishResponse>

}