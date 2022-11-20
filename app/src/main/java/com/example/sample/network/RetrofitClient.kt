package com.example.sample.network

import android.util.Base64.encodeToString
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Base64

object RetrofitClient {


    private const val BASE_URL = "http://10.0.2.2:3000/";
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}