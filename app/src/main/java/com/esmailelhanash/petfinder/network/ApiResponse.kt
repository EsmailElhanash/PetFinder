package com.esmailelhanash.petfinder.network

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ApiResponse(
    // Other fields you may have
    val data: Any // This represents the entire response
)

//class AnimalDeserializer : JsonDeserializer<List<Animal>> {
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): List<Animal> {
//        val animalsJson = json?.asJsonObject?.getAsJsonArray("data")
//        return Gson().fromJson(animalsJson, typeOfT)
//    }
//}

// In your Retrofit setup

// Your API service interface remains the same
