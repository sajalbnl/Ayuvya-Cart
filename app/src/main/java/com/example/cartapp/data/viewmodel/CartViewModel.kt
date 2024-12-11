package com.example.cartapp.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartapp.data.model.CartItem
import com.example.cartapp.data.model.ProductData
import com.example.cartapp.db.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor (private val cartDao: CartDao) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems


    init {
        fetchCartItems()
    }

    fun fetchCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentItems = cartDao.getAllCartItems()
            withContext(Dispatchers.Main) {
                if (_cartItems.value != currentItems) {
                    _cartItems.emit(currentItems)
                }
            }
        }
    }

    fun addOrUpdateCartItem(product: ProductData) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingItem = cartDao.getCartItemByProductId(product.id)
            if (existingItem != null) {
                // Increment quantity if item already exists
                cartDao.updateCartItemQuantity(product.id, existingItem.quantity + 1)
            } else {
                // Add new cart item
                cartDao.insertCartItem(CartItem(product = product, quantity = 1))
            }
            fetchCartItems()
        }
    }

    fun deleteCartItem(productId: Int) {
        viewModelScope.launch {
            cartDao.deleteCartItemByProductId(productId)
            fetchCartItems()
        }
    }

    fun increaseQuantity(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.updateCartItemQuantity(cartItem.product.id, cartItem.quantity + 1)
            fetchCartItems()
        }
    }

    fun decreaseQuantity(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cartItem.quantity > 1) {
                cartDao.updateCartItemQuantity(cartItem.product.id, cartItem.quantity - 1)
            } else {
                // Remove the item if quantity is 1
                cartDao.deleteCartItem(cartItem)
            }
            fetchCartItems()
        }
    }
}

