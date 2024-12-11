package com.example.cartapp.data.repository

import com.example.cartapp.data.api.ProductApi
import com.example.cartapp.data.model.ProductData
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) {
    suspend fun getProducts(): List<ProductData> {
        return productApi.getProducts()
    }
}
