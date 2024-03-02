package com.esmailelhanash.petfinder.network

import android.content.Context
import com.esmailelhanash.petfinder.BuildConfig
import com.esmailelhanash.petfinder.models.Animal
import com.esmailelhanash.petfinder.models.AnimalType
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.await


object Network {

    private const val BASE_URL = "https://api.petfinder.com/v2/"
    private var retrofit: Retrofit? = RetrofitClient.getClient(BASE_URL)
    private var apiService: ApiService = retrofit!!.create(ApiService::class.java)

    private const val ACCESS_TOKEN_KEY = "access_token"


    private suspend fun fetchAccessToken(): String? {
        return apiService.getAccessToken("client_credentials", BuildConfig.PETFINDER_API_KEY, BuildConfig.PETFINDER_SECRET).body()?.accessToken
    }

    private fun saveAccessTokenToPrefs(accessToken: String,context: Context) {
        // Use your SharedPreferences implementation to save the access token
        // For example, using Android's SharedPreferences:
        val sharedPreferences = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN_KEY, accessToken)
        editor.apply()
    }

    private fun getAccessTokenFromPrefs(context: Context): String? {
        // Retrieve the access token from SharedPreferences
        val sharedPreferences = context.getSharedPreferences("your_prefs_name", Context.MODE_PRIVATE)
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
    }



    suspend fun getAllAnimals(context: Context): List<Animal>? {
        var accessToken = getAccessTokenFromPrefs(context)

        try {
            return apiService.getAllAnimals("Bearer $accessToken").await().animals
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                // If the error code is 401, fetch a new access token
                accessToken = fetchAccessToken()

                // Save the new access token to SharedPreferences
                accessToken?.let { saveAccessTokenToPrefs(it,context) }

                // Retry the API call with the new access token
                return apiService.getAllAnimals("Bearer $accessToken").await().animals
            } else {
                e.printStackTrace()
                return null
            }
        }
    }
    // get more animals, with a new page number as a query parameter
    suspend fun getMoreAnimals(context: Context, page: Int): List<Animal>? {
        var accessToken = getAccessTokenFromPrefs(context)

        try {
            return apiService.getMoreAnimals("Bearer $accessToken", page).await().animals
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                accessToken = fetchAccessToken()
                accessToken?.let { saveAccessTokenToPrefs(it, context) }
                return apiService.getMoreAnimals("Bearer $accessToken", page).await().animals
            } else {
                e.printStackTrace()
                return null
            }
        }
    }

    suspend fun getMoreAnimalsOfType(context: Context, page: Int, type: String): List<Animal>? {
        var accessToken = getAccessTokenFromPrefs(context)

        try {
            return apiService.getMoreAnimalsOfType("Bearer $accessToken", type, page).await().animals
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                accessToken = fetchAccessToken()
                accessToken?.let { saveAccessTokenToPrefs(it,context) }
                return apiService.getMoreAnimalsOfType("Bearer $accessToken", type, page).await().animals
            } else {
                e.printStackTrace()
                return null
            }
        }
    }

    // get animals of type
    suspend fun getAnimalsOfType(context: Context, type: String): List<Animal>? {
        var accessToken = getAccessTokenFromPrefs(context)

        try {
            return apiService.getAnimalsOfType("Bearer $accessToken", type).await().animals
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                accessToken = fetchAccessToken()
                accessToken?.let { saveAccessTokenToPrefs( it, context) }
                return apiService.getAnimalsOfType("Bearer $accessToken", type).await().animals
            } else {
                e.printStackTrace()
                return null
            }
        }
    }

    // get all types of animals
    suspend fun getAllTypes(context: Context): List<AnimalType>? {
        var accessToken = getAccessTokenFromPrefs(context)

        try {
            return apiService.getAllTypes("Bearer $accessToken").await().animalTypes
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 401) {
                accessToken = fetchAccessToken()
                accessToken?.let { saveAccessTokenToPrefs(it, context) }
                return apiService.getAllTypes("Bearer $accessToken").await().animalTypes
            } else {
                e.printStackTrace()
                return null
            }
        }
    }

}