package com.sathishkumar.varadhismartektask.netwrok

import com.sathishkumar.varadhismartektask.model.ResponseData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


// Created by Sathish
interface UserAPI {

    @FormUrlEncoded
    @POST("get_monthly_appointments")
    fun get_booking_appointment_list(
        @Field("user_employeid") employeId: String?,
        @Field("status") page_no: String?,
        @Field("appointment_type") appointment_type: String?,
        @Field("month") appointment_date: String?
    ): Call<ResponseData>

}