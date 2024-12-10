package com.example.cartapp

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    // for home
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.home,
        icon_focused = R.drawable.home_active
    )

    object Products : BottomBarScreen(
        "allProducts",
        title = "Products",
        icon = R.drawable.products,
        icon_focused = R.drawable.products_active
    )
    object Profile : BottomBarScreen(
        "profile",
        title = "Profile",
        icon = R.drawable.profile,
        icon_focused = R.drawable.profile_active
    )
}