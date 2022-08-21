package com.example.android.devbyteviewer.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

    object Network {
        // Configure retrofit to parse JSON and use coroutines
        private val retrofit = Retrofit.Builder()
                .baseUrl("https://devbytes.udacity.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val devbytes = retrofit.create(DevbyteService::class.java)
    }

    interface DevbyteService {
        @GET("devbytes.json")
        //    fun getPlaylist(): Deferred<NetworkVideoContainer>
        suspend fun getPlaylist(): NetworkVideoContainer
    }