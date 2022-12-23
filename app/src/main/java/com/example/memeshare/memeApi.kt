package com.example.memeshare

import retrofit2.Response
import retrofit2.http.GET

interface memeApi {

    @GET("/gimme")
    suspend fun getMeme(): Response<meme>

}