package com.esmailelhanash.petfinder.network

import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun getAccessToken() {
    try {
        val token = getAccessTokenP()
        println("Access Token: $token")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun getAccessTokenP(): String {
    val url = URL("https://api.petfinder.com/v2/oauth2/token")
    val connection = url.openConnection() as HttpURLConnection

    // Set up the request method and headers
    connection.requestMethod = "POST"
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

    // Enable input/output streams
    connection.doOutput = true

    // Set request parameters
    val params = "grant_type=client_credentials&client_id={CLIENT-ID}&client_secret={CLIENT-SECRET}"

    // Write the parameters to the request
    val outputStream = DataOutputStream(connection.outputStream)
    outputStream.writeBytes(params)
    outputStream.flush()
    outputStream.close()

    // Get the response
    val responseCode = connection.responseCode
    return if (responseCode == HttpURLConnection.HTTP_OK) {
        val reader = BufferedReader(InputStreamReader(connection.inputStream))
        val response = StringBuilder()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            response.append(line)
        }
        reader.close()
        response.toString()
    } else {
        throw Exception("Failed to get access token. Response code: $responseCode")
    }
}
