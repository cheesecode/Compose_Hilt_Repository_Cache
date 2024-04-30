package com.example.test.service

import com.example.test.model.Product
import com.example.test.model.ProductResponse
import com.example.test.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("products/{productId}")
    suspend fun getProduct(@Path("productId") productId: Int): Product

    @GET("products/")
    suspend fun getAllProducts(): ProductResponse

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    @GET("users/")
    suspend fun getAllUsers(): List<User>

}