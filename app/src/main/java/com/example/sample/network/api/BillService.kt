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
import java.time.DayOfWeek

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

    @GET("bill/filter")
    fun filterBill(@Query("fd")fd: Int, @Query("fm")fm: Int, @Query("fy")fy: Int,
                   @Query("td")td: Int, @Query("tm")tm: Int, @Query("ty")ty: Int): Call<BillFilterResponse>

    @PUT("bill/updatebpdish")
    fun updateBillBpDish(@Body updateBillBpDish: UpdateBillBpDish): Call<DefaultResponse>

}