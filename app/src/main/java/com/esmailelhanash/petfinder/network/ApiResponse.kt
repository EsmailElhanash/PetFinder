package com.esmailelhanash.petfinder.network

import com.esmailelhanash.petfinder.Animal
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

data class ApiResponse(
    val ak: List<Animal>
)

class ApiResponseDeserializer : JsonDeserializer<ApiResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ApiResponse {
        val akObject = json?.asJsonObject?.getAsJsonObject("ak")
        val animals = Gson().fromJson(akObject?.getAsJsonArray("value"), List::class.java)
        return ApiResponse(animals as List<Animal>)
    }
}