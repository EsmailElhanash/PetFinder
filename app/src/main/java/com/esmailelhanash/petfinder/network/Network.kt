package com.esmailelhanash.petfinder.network

import com.esmailelhanash.petfinder.BuildConfig
import com.esmailelhanash.petfinder.models.Animal
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse


object Network {

    private const val BASE_URL = "https://api.petfinder.com/v2/"
    private var retrofit: Retrofit? = RetrofitClient.getClient(BASE_URL)
    private var apiService: ApiService = retrofit!!.create(ApiService::class.java)

    private suspend fun fetchAccessToken() : String? {
         return  apiService.getAccessToken("client_credentials", BuildConfig.PETFINDER_API_KEY, BuildConfig.PETFINDER_SECRET).body()?.accessToken
    }

    suspend fun getAllAnimals() : List<Animal>? {
        val accessToken = fetchAccessToken()
        return try {
            apiService.getAllAnimals("Bearer $accessToken").await().animals
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // get animals of type
    suspend fun getAnimalsOfType(type: String) : List<Animal>? {
        val accessToken =  fetchAccessToken()
        return try {
            apiService.getAnimalsOfType("Bearer $accessToken", type).await().animals
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}