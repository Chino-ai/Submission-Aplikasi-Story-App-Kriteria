package com.example.submissionaplikasistoryappkriteria.api

import com.example.submissionaplikasistoryappkriteria.data.remote.remote.*
import com.example.submissionaplikasistoryappkriteria.ui.register.UserPreference
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import okhttp3.RequestBody
import retrofit2.http.Multipart
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {

    @Multipart
    @POST("/v1/stories")
    fun uploadImage(
        @Header("Authorization") bearer: String?,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody?,
    ): Call<PhotoUploadResponse>

    @FormUrlEncoded
    @POST("/v1/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/v1/login")
    fun Login(
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<GetLoginResponse>


    @GET("/v1/stories")
    fun getAllStories(
        @Header("Authorization") bearer: String?
    ): Call<GetAllStoriesResponse>
}

class ApiConfig {

    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://story-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

