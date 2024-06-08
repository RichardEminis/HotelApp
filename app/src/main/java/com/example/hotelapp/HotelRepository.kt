package com.example.cleverpumpkinhotel

import android.content.Context
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

class HotelRepository(context: Context) {
    private val contentType = "application/json".toMediaType()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/iMofas/ios-android-test/master/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    private val service: ApiService = retrofit.create(ApiService::class.java)
}