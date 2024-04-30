package com.example.test.repository

import com.example.test.model.Product
import com.example.test.model.ProductResponse
import com.example.test.model.User

interface Repository {
    suspend fun fetchUser(userId: Int): User
    suspend fun getAllUsers(): List<User>
    suspend fun getProduct( productId: Int): Product
    suspend fun getAllProducts(): ProductResponse




}