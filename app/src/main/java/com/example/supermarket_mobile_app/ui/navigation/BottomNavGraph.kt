// ui/navigation/BottomNavGraph.kt
package com.example.supermarket_mobile_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.supermarket_mobile_app.ui.cart.CartScreen
import com.example.supermarket_mobile_app.ui.cart.CartViewModel
import com.example.supermarket_mobile_app.ui.home.HomeScreen
import com.example.supermarket_mobile_app.ui.home.HomeViewModel

/**
 * Ο Composable γράφος πλοήγησης για τις οθόνες που ελέγχονται από την κάτω μπάρα πλοήγησης.
 * @param mainNavController Ο κεντρικός NavController της εφαρμογής, χρησιμοποιείται για πλοήγηση
 * σε οθόνες που βρίσκονται "πάνω" από την κύρια οθόνη (π.χ. οθόνη λεπτομερειών).
 * @param bottomNavController Ο NavController που διαχειρίζεται την εναλλαγή μεταξύ των
 * οθονών της κάτω μπάρας (Αρχική, Καλάθι, κ.λπ.).
 * @param modifier Ένα Modifier που περνιέται από το Scaffold για να εφαρμοστεί το σωστό padding.
 */
@Composable
fun BottomNavGraph(
    mainNavController: NavHostController,
    bottomNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Το NavHost είναι το Composable που φιλοξενεί τις οθόνες του γράφου.
    NavHost(
        navController = bottomNavController,
        startDestination = BottomNavItem.Home.route, // Η αρχική οθόνη είναι η "Αρχική".
        modifier = modifier
    ) {
        // Ορισμός της οθόνης για τη διαδρομή "home".
        composable(route = BottomNavItem.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = homeViewModel,
                // Όταν ο χρήστης πατήσει ένα προϊόν, χρησιμοποιούμε τον mainNavController
                // για να πλοηγηθούμε στην οθόνη ProductDetails.
                onProductClick = { productId ->
                    mainNavController.navigate(Screen.ProductDetails.withArgs(productId.toString()))
                }
            )
        }

        // Ορισμός της οθόνης για τη διαδρομή "cart".
        composable(route = BottomNavItem.Cart.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            CartScreen(viewModel = cartViewModel)
        }

        // Εδώ μπορούν να προστεθούν στο μέλλον και άλλες οθόνες, όπως της Λίστας Επιθυμιών.
    }
}