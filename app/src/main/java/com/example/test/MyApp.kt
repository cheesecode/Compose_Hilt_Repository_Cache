package com.example.test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()



    }
}
