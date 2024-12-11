package com.example.cartapp.data.api

import com.example.cartapp.data.model.ProductData
import retrofit2.http.GET

interface ProductApi {
    @GET("products")
    suspend fun getProducts(): List<ProductData>
}
