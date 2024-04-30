package com.example.test.repository

import android.content.Context
import com.example.test.model.Product
import com.example.test.model.ProductResponse
import com.example.test.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import javax.inject.Inject

class Cache @Inject constructor(private val context: Context) {

    companion object {
        private const val USER_CACHE_FILE = "user_cache.json"
        private const val PRODUCT_CACHE_FILE = "product_cache.json"
    }

    // User cache methods
    suspend fun getUser(userId: Int): User? {
        val cachedUsers = readUserCache()
        return cachedUsers.find { it.id == userId }
    }

    suspend fun getAllUsers(): List<User> {
        return readUserCache()
    }

    suspend fun updateUser(user: User) {
        val cachedUsers = readUserCache().toMutableList()
        val existingUserIndex = cachedUsers.indexOfFirst { it.id == user.id }
        if (existingUserIndex != -1) {
            cachedUsers[existingUserIndex] = user
        } else {
            cachedUsers.add(user)
        }
        writeUserCache(cachedUsers)
    }

    suspend fun updateAllUsers(users: List<User>) {
        writeUserCache(users)
    }

    // Product cache methods
    suspend fun getProduct(productId: Int): Product? {
        val cachedProducts = readProductCache()
        return cachedProducts?.products?.find { it.id == productId }
    }

    suspend fun getAllProducts(): ProductResponse? {
        return readProductCache()
    }

    suspend fun updateProduct(product: Product) {
        var cachedProducts = readProductCache() ?: return // Return if cache is null

        val existingProductIndex = cachedProducts.products.indexOfFirst { it.id == product.id }
        if (existingProductIndex != -1) {
            cachedProducts = cachedProducts.copy(products = cachedProducts.products.toMutableList().apply {
                set(existingProductIndex, product)
            })
        } else {
            // Product not found, add it to the list
            cachedProducts = cachedProducts.copy(products = cachedProducts.products.toMutableList().apply {
                add(product)
            })
        }

        writeProductCache(cachedProducts)
    }


    suspend fun updateAllProducts(products: ProductResponse) {
        writeProductCache(products)
    }

    // Read/write methods for user cache
    private suspend fun readUserCache(): List<User> {
        return try {
            val file = File(context.cacheDir, USER_CACHE_FILE)
            if (!file.exists()) {
                emptyList()
            } else {
                val json = file.readText()
                Gson().fromJson(json, object : TypeToken<List<User>>() {}.type)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun writeUserCache(users: List<User>) {
        try {
            val file = File(context.cacheDir, USER_CACHE_FILE)
            val json = Gson().toJson(users)
            file.writeText(json)
        } catch (e: Exception) {
            // Handle write error
        }
    }

    // Read/write methods for product cache
    private suspend fun readProductCache(): ProductResponse? {
        return try {
            val file = File(context.cacheDir, PRODUCT_CACHE_FILE)
            if (!file.exists()) {
                null
            } else {
                val json = file.readText()
                Gson().fromJson(json, ProductResponse::class.java)
            }
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun writeProductCache(products: ProductResponse) {
        try {
            val file = File(context.cacheDir, PRODUCT_CACHE_FILE)
            val json = Gson().toJson(products)
            file.writeText(json)
        } catch (e: Exception) {
            // Handle write error
        }
    }
}
