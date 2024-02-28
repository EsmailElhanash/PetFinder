package com.esmailelhanash.petfinder.network

import com.esmailelhanash.petfinder.Animal
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @FormUrlEncoded
    @POST("oauth2/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<AccessTokenResponse>

    // get all animals:
    @GET("animals")
    fun getAllAnimals(@Header("Authorization") accessToken: String): Call<Any>


}
