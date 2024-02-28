package com.esmailelhanash.petfinder.network

import com.esmailelhanash.petfinder.Animal
import com.esmailelhanash.petfinder.BuildConfig
import retrofit2.Retrofit
import retrofit2.await


object Network {

    private const val BASE_URL = "https://api.petfinder.com/v2/"
    private var retrofit: Retrofit? = RetrofitClient.getClient(BASE_URL)
    private var apiService: ApiService = retrofit!!.create(ApiService::class.java)

    private suspend fun fetchAccessToken() : String? {
         return  apiService.getAccessToken("client_credentials", BuildConfig.PETFINDER_API_KEY, BuildConfig.PETFINDER_SECRET).body()?.accessToken
    }

    suspend fun getAllAnimals(){
        val accessToken =  fetchAccessToken()
        apiService.getAllAnimals("Bearer $accessToken").await().apply {
            this
        }
    }


}