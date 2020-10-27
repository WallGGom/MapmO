package com.example.frontend

import retrofit2.Call
import retrofit2.http.*

interface SignupService{
    @FormUrlEncoded
    @POST("/api/accounts/account/")
    fun requestSignup(
        @Field("username") username:String,
        @Field("password1") password1:String,
        @Field("password2") password2:String
    ) : Call<Signup>

    @GET("/api/accounts/account/")
    fun requestCheck(
        @Query("username") username:String,
    ) : Call<Check>
}



