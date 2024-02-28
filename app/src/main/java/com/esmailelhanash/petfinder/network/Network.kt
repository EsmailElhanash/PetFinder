package com.esmailelhanash.petfinder.network

import com.esmailelhanash.petfinder.network.RetrofitClient.getClient
import retrofit2.Retrofit





object Network {

    private const val BASE_URL = "https://api.petfinder.com/v2/animals"
    private var retrofit: Retrofit? = getClient(BASE_URL)
    var apiService: ApiService = retrofit!!.create(ApiService::class.java)

    // base api

    // a function to make a call to the pet finder api using retrofit


}