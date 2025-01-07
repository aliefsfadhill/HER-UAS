package com.alief_datapegawai.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "http://192.168.16.99/"

//    private const val BASE_URL = "http://0.0.0.0/)"


    private fun interceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request().newBuilder()
//                .addHeader("Content-Type", "application/json")
//                .build()
//            chain.proceed(request)
//        }
//        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(interceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}




