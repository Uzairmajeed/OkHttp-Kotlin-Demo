package com.facebook.okhttp_kt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.FormBody

class MainActivity : AppCompatActivity() {
    private lateinit var buttonget: Button
    private lateinit var buttonpost: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonget=findViewById(R.id.getbutton)
        buttonpost=findViewById(R.id.postbutton)
        textView=findViewById(R.id.textview)
        val networkOperations=NetworkOperations()

        buttonget.setOnClickListener(){
            networkOperations.performGetRequest("https://dummyjson.com/products/5",object :NetworkOperations.NetworkCallback{
                override fun onSuccess(response: String) {
                    updateTextView(response)
                }

                override fun onError(e: Exception) {
                    e.printStackTrace()
                }
            })
        }

        buttonpost.setOnClickListener(){
            val requestBody = FormBody.Builder()
                .add("Name", "uzair")
                .build()
            networkOperations.performPostRequest("https://reqres.in/api/users",requestBody,object :NetworkOperations.NetworkCallback{
                override fun onSuccess(response: String) {
                    Log.d("POST Response", response) // Add this line to log the response
                    updateTextView(response)
                }

                override fun onError(e: Exception) {
                    e.printStackTrace()
                }
            })
        }
    }

    private fun updateTextView(text: String) {
        runOnUiThread {
            textView.text = text
        }
    }

}