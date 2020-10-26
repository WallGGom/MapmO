package com.example.frontend

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginService{
    @FormUrlEncoded
    @POST("/api/accounts/login/")
    fun requestLogin(
        @Field("username") username:String,
        @Field("password") password:String,
    ) : Call<Login> }

