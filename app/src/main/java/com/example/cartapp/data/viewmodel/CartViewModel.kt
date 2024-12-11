package com.example.cartapp.data.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cartapp.data.model.CartItem
import com.example.cartapp.data.model.ProductData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    fun addToCart(product: ProductData) {
        // Check if the product is already in the cart
        val existingItem = _cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            // Increase the quantity
            existingItem.quantity++
        } else {
            // Add new product to the cart
            _cartItems.add(CartItem(product = product, quantity = 1))
        }
    }

    fun increaseQuantity(productId: Int) {
        _cartItems.find { it.product.id == productId }?.let {
            it.quantity++
        }
    }

    fun decreaseQuantity(productId: Int) {
        val item = _cartItems.find { it.product.id == productId }
        item?.let {
            if (it.quantity > 1) {
                it.quantity--
            } else {
                _cartItems.remove(it) // Remove item if quantity becomes 0
            }
        }
    }
}

