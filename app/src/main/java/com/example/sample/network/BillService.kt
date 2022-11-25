package com.example.sample.network

import com.example.sample.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface BillService {

    @Headers("Accept: application/json")
    @POST("bill/create")
    fun createBill(@Body createBillRequest: CreateBillRequest): Call<BillResponse>

    @Headers("Accept: application/json")
    @POST("bill/adddish")
    fun addDish(@Body addDishRequest: AddDishRequest): Call<BillAddDishResponse>

    @GET("bill/getalldish")
    fun getAllBpDish(@Query("id") id: Int): Call<GetAllBpDishResponse>

}