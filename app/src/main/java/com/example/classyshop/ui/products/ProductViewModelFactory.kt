package com.example.classyshop.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.classyshop.repository.ProductRepository

class ProductViewModelFactory (private val repository: ProductRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create (modelclass: Class<T>): T{
        if (modelclass.isAssignableFrom(ProductViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel() as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")


    }

}