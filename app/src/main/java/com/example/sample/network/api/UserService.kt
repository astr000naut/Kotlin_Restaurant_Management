package com.example.sample.network.api


import com.example.sample.model.apirequest.CreateUser
import com.example.sample.model.apirequest.UpdateUser
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.UserListResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("user/getallpersonnel")
    fun getALlPersonnel(): Call<UserListResponse>

    @PUT("user/update")
    fun updateUser(@Body updateUser: UpdateUser): Call<UserListResponse>

    @DELETE("user/delete/{id}")
    fun deleteUser(@Path("id") id: Int): Call<DefaultResponse>

    @POST("user/create")
    fun createUser(@Body createUser: CreateUser): Call<DefaultResponse>
}