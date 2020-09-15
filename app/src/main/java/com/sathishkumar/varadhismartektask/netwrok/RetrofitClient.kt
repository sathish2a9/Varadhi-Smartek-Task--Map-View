package com.sathishkumar.varadhismartektask.netwrok

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Created by Sathish
object RetrofitClient{

    val userAPI = retrofit()   // returns instance of PostsAPI interface
        .create(UserAPI::class.java)

    private fun retrofit() = Retrofit.Builder()  // returns retrofit instance
        .baseUrl("http://13.127.95.246:7000/marketing/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}