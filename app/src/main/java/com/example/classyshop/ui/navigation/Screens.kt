package com.example.classyshop.ui.navigation

sealed class Screens(val route: String) {
    data object ProductsScreen : Screens("products_screen")
    data object ProductDetailsScreen : Screens("product_details_screen?product={product}")
    data object CartScreen : Screens("cart_screen")
    data object OrderPlacedScreen : Screens("order_placed_screen")


}