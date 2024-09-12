package com.example.classyshop.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classyshop.data.Product
import com.example.classyshop.repository.ProductRepository
import com.example.classyshop.ui.checkout.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart = _cart.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                _error.value = "Failed to load products :${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentCart = _cart.value.toMutableList()
            val existingItem = currentCart.find { it.product.id == product.id }
            if (existingItem != null) {
                existingItem.quantity++
                Log.d(
                    "ProductViewModel",
                    "Quantity increased for product: ${product.title}.New quantity : ${existingItem.quantity}"
                )
            } else {
                currentCart.add(CartItem(product))
                Log.d("ProductViewModel", "Product added to cart: ${product.title}")

            }
            _cart.value = currentCart
            logCartContents()
        }

    }

    private fun logCartContents() {
        Log.d("ProductViewModel", "Current Cart contents:")
        _cart.value.forEach { item ->
            Log.d("ProductViewModel", "${item.product.title} - Quantity: ${item.quantity}")
        }
    }

    fun getCartItemCount(): Int {
        return _cart.value.sumOf { it.quantity }
    }


    fun increaseQuantity(product: Product) {
        viewModelScope.launch {
            val currentCart = _cart.value.toMutableList()
            val item = currentCart.find { it.product.id == product.id }
            if (item != null) {
                item.quantity++
                _cart.value = currentCart
            }
        }
    }

    fun decreaseQuantity(product: Product) {
        viewModelScope.launch {
            val currentCart = _cart.value.toMutableList()
            val item = currentCart.find { it.product.id == product.id }
            if (item != null) {
                if (item.quantity > 1) {
                    item.quantity--
                } else {
                    currentCart.remove(item)
                }
                _cart.value = currentCart
            }
        }

    }

    fun removeFromCart(product: Product) {
        viewModelScope.launch {
            val currentCart = _cart.value.toMutableList()
            val item = currentCart.find { it.product.id == product.id }
            _cart.value = currentCart
        }
    }


    fun clearCart() {

        _cart.value = emptyList()


    }
}

