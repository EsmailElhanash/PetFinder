package com.esmailelhanash.petfinder.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String?): Retrofit? {
        if (retrofit == null) {
            retrofit = baseUrl?.let {
                Retrofit.Builder()
                    .baseUrl(it)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
        return retrofit
    }
}
