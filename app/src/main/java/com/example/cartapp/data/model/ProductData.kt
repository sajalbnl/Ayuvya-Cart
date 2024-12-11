package com.example.cartapp.data.model

import java.io.Serializable

data class ProductData(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating,
): Serializable


data class Rating(
    val rate: Double,
    val count: Int
): Serializable

data class CartItem(
    val product: ProductData,
    var quantity: Int
)