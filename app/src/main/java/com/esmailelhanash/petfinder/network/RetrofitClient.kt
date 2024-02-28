package com.esmailelhanash.petfinder.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory;


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            val gson = GsonBuilder()
                .registerTypeAdapter(ApiResponse::class.java, ApiResponseDeserializer())
                .create()
            retrofit = baseUrl?.let {
                Retrofit.Builder()
                    .baseUrl(it)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
        }
        return retrofit
    }
}
