package com.example.cartapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartapp.data.model.ProductData
import com.example.cartapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<List<ProductData>>()
    val products: LiveData<List<ProductData>> get() = _products

    // Fetch products from repository
    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val productList = repository.getProducts()
                _products.postValue(productList as List<ProductData>?) // Update LiveData
            } catch (e: Exception) {
                // Handle error (log it, show error state, etc.)
                _products.postValue(emptyList()) // Fallback to an empty list
            }
        }
    }
}

