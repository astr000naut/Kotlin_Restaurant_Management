package com.example.sample.network.api

import com.example.sample.model.*
import com.example.sample.model.apirequest.AddDishRequest
import com.example.sample.model.apiresponse.BillAddDishResponse
import com.example.sample.model.apirequest.CreateBillRequest
import com.example.sample.model.apirequest.UpdateBillBpDish
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.GetListBpDishResponse
import retrofit2.Call
import retrofit2.http.*

interface BillService {

    @Headers("Accept: application/json")
    @POST("bill/create")
    fun createBill(@Body createBillRequest: CreateBillRequest): Call<BillResponse>

    @Headers("Accept: application/json")
    @POST("bill/adddish")
    fun addDish(@Body addDishRequest: AddDishRequest): Call<BillAddDishResponse>

    @GET("bill/getalldish")
    fun getAllBpDish(@Query("id") id: Int): Call<GetListBpDishResponse>

    @GET("bill/getone")
    fun getBill(@Query("id") id: Int): Call<BillResponse>

    @PUT("bill/updatebpdish")
    fun updateBillBpDish(@Body updateBillBpDish: UpdateBillBpDish): Call<DefaultResponse>

}