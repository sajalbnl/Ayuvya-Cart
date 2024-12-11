package com.example.cartapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded
import java.io.Serializable

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Unique ID for each cart item

    @Embedded(prefix = "product_")
    val product: ProductData, // Product details

    var quantity: Int // Quantity of the product
): Serializable

data class ProductData(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    @Embedded(prefix = "rating_")
    val rating: Rating
): Serializable

data class Rating(
    val rate: Double,
    val count: Int
): Serializable
