package com.example.memeshare

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object retrofitInstance {

        fun getInstance(): Retrofit {
                return Retrofit.Builder().baseUrl("https://meme-api.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

}