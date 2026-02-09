package com.cleivercoelho.skeleton.core.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

// Routes.kt
@Serializable object ProductsGraph
@Serializable object ProductList
@Serializable data class ProductDetail(val productId: Int)

@Serializable object CartGraph
@Serializable object Cart
@Serializable data class Checkout(val totalAmount: Double)

// ProductsNavigation.kt
fun NavGraphBuilder.productsGraph(
    onProductClick: (Int) -> Unit,
    onAddToCart: () -> Unit
) {
    navigation<ProductsGraph>(startDestination = ProductList) {
        composable<ProductList> {
            ProductListScreen(onProductClick = onProductClick)
        }
        composable<ProductDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<ProductDetail>()
            ProductDetailScreen(
                productId = args.productId,
                onAddToCart = onAddToCart
            )
        }
    }
}

// ProductListScreen.kt
@Composable
fun ProductListScreen(onProductClick: (Int) -> Unit) {
}

// ProductDetailScreen.kt
@Composable
fun ProductDetailScreen(productId: Int, onAddToCart: () -> Unit) {}

// CartNavigation.kt
fun NavGraphBuilder.cartGraph(
    onCheckout: (Double) -> Unit,
    onOrderComplete: () -> Unit
) {
    navigation<CartGraph>(startDestination = Cart) {
        composable<Cart> {
            CartScreen(onCheckout = { onCheckout(99.99) })
        }
        composable<Checkout> { backStackEntry ->
            val args = backStackEntry.toRoute<Checkout>()
            CheckoutScreen(
                total = args.totalAmount,
                onComplete = onOrderComplete
            )
        }
    }
}

// CartScreen.kt
@Composable
fun CartScreen(onCheckout: () -> Unit) {
}

// CheckoutScreen.kt
@Composable
fun CheckoutScreen(total: Double, onComplete: () -> Unit) {
}

// MainActivity.kt
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopApp()
        }
    }
}

// ShopApp.kt
@Composable
fun ShopApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(ProductsGraph) },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Products") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(CartGraph) },
                    icon = { Icon(Icons.Default.ShoppingCart, null) },
                    label = { Text("Cart") }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = ProductsGraph,
            modifier = Modifier.padding(padding)
        ) {
            productsGraph(
                onProductClick = { navController.navigate(ProductDetail(it)) },
                onAddToCart = { navController.navigate(CartGraph) }
            )
            cartGraph(
                onCheckout = { navController.navigate(Checkout(it)) },
                onOrderComplete = {
                    navController.navigate(ProductsGraph) {
                        popUpTo(ProductsGraph) { inclusive = true }
                    }
                }
            )
        }
    }
}
