package com.example.classyshop.repository

import com.example.classyshop.data.Product
import com.example.classyshop.data.network.NetworkManager

class ProductRepository {
    suspend fun getProducts(): List<Product> {
        return NetworkManager.fetchProducts()
    }
}