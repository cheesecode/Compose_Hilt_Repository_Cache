package com.example.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.model.Product
import com.example.test.repository.Repository
import com.example.test.service.Service
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor( private val repository: Repository) : ViewModel() {

    private val _productFlow = MutableStateFlow<Product?>(null)
    val productFlow: Flow<Product?> = _productFlow.asStateFlow()

    private val _productsFlow = MutableStateFlow<List<Product>?>(null)
    val productsFlow: StateFlow<List<Product>?> = _productsFlow.asStateFlow()

    init {
      //  fetchProduct(3)

      //  fetchProducts(30)
    }


    fun fetchProduct(productId: Int) {
        viewModelScope.launch {
            try {
                val product = repository.getProduct(productId)
                _productFlow.value = product
            } catch (e: Exception) {
                // Handle error
                _productFlow.value = null
                e.printStackTrace()
            }
        }
    }

    fun fetchProducts(amount: Int) {
        viewModelScope.launch {
            try {
                val allProducts = repository.getAllProducts()
                val limitedProducts = allProducts.products.asFlow()
                    .take(amount)
                    .toList()

                _productsFlow.value = limitedProducts
            } catch (e: Exception) {
                // Handle error
                _productsFlow.value = null
                e.printStackTrace()
            }
        }
    }
}
