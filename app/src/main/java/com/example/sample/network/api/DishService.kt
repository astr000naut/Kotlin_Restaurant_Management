package com.example.sample.network.api

import com.example.sample.model.apiresponse.GetAllDishResponse
import com.example.sample.model.apiresponse.GetListBpDishResponse
import retrofit2.Call
import retrofit2.http.GET


interface DishService {

    @GET("dish/getall")
    fun getAllDish(): Call<GetAllDishResponse>

    @GET("dish/getunfinished")
    fun getUnfinishedDish(): Call<GetListBpDishResponse>
}