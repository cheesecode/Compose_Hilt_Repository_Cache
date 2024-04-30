package com.example.test.repository


import com.example.test.model.Product
import com.example.test.model.ProductResponse
import com.example.test.model.User
import com.example.test.service.Service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val service: Service,
    private val cache: Cache
) : Repository {

    override suspend fun fetchUser(userId: Int): User {
        // First, try to fetch user from the cache
        val cachedUser = cache.getUser(userId)
        if (cachedUser != null) {
            return cachedUser
        }

        // If not available in cache, fetch user from the service
        val user = service.getUser(userId)

        // Update cache with fetched user
        cache.updateUser(user)

        return user
    }

    override suspend fun getAllUsers(): List<User> {
        val cachedUsers = cache.getAllUsers()
        return if (cachedUsers.isNotEmpty()) {
            cachedUsers
        } else {
            val users = service.getAllUsers()
            cache.updateAllUsers(users)
            users
        }
    }

    override suspend fun getProduct(productId: Int): Product {
        // First, try to fetch product from the cache
        val cachedProduct = cache.getProduct(productId)
        if (cachedProduct != null) {
            return cachedProduct
        }

        // If not available in cache, fetch product from the service
        val product = service.getProduct(productId)

        // Update cache with fetched product
        cache.updateProduct(product)

        return product
    }

    override suspend fun getAllProducts(): ProductResponse {
        val cachedProducts = cache.getAllProducts()
        return if (cachedProducts != null) {
            cachedProducts
        } else {
            val products = service.getAllProducts()
            cache.updateAllProducts(products)
            products
        }
    }
}