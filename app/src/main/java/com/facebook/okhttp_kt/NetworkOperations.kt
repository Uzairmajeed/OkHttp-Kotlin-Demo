package com.facebook.okhttp_kt

import okhttp3.*
import java.io.IOException

class NetworkOperations {
    private  var client:OkHttpClient
    init {
        client=OkHttpClient.Builder()
              .build()
    }
    fun performGetRequest(url: String, callback: NetworkCallback) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body?.string()
                    myResponse?.let { callback.onSuccess(it) }
                }
            }
        })
    }

    fun performPostRequest(url: String, requestBody: RequestBody, callback: NetworkCallback) {
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback.onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body?.string()
                    myResponse?.let { callback.onSuccess(it) }
                }
            }
        })
    }

    // Define a callback interface to handle network responses
    interface NetworkCallback {
        fun onSuccess(response: String)
        fun onError(e: Exception)
    }
}