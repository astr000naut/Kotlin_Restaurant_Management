package com.example.sample.network.api

import com.example.sample.model.apirequest.CreateDishRequest
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.GetAllDishResponse
import com.example.sample.model.apiresponse.GetListBpDishResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface DishService {

    @GET("dish/getall")
    fun getAllDish(): Call<GetAllDishResponse>

    @GET("dish/getunfinished")
    fun getUnfinishedDish(): Call<GetListBpDishResponse>

    @POST("dish/add")
    fun createDish(@Body createDishRequest: CreateDishRequest): Call<DefaultResponse>

    @DELETE("dish/delete/{id}")
    fun deleteDish(@Path("id") id: Int): Call<DefaultResponse>
}