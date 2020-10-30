package com.example.frontend

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UploadService {
    @Multipart
    @POST("/api/memos/upload/")
    fun requestUpload(
//        @Part("memoPk") memoPk: String,
//        @Part("category") category: String?,
        @Part File: MultipartBody.Part
    ): Call<Upload>
}

