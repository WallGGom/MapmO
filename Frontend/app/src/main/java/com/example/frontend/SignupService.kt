package com.example.frontend

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SignupService{
    @FormUrlEncoded
    @POST("/api/accounts/account/")
    fun requestSignup(
        @Field("username") username:String,
        @Field("password1") password1:String,
        @Field("password2") password2:String
    ) : Call<Signup> }

