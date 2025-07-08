// ui/navigation/NavGraph.kt
package com.example.supermarket_mobile_app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.supermarket_mobile_app.ui.MainScreen
import com.example.supermarket_mobile_app.ui.details.ProductDetailsScreen
import com.example.supermarket_mobile_app.ui.details.ProductDetailsViewModel
import com.example.supermarket_mobile_app.ui.login.LoginScreen
import com.example.supermarket_mobile_app.ui.login.LoginViewModel

/**
 * Ο Composable γράφος πλοήγησης που ορίζει όλες τις πιθανές διαδρομές (routes)
 * και τις συνδέει με τις αντίστοιχες οθόνες (Composables).
 * @param navController Ο κεντρικός NavController που διαχειρίζεται την πλοήγηση.
 */
@Composable
fun NavGraph(navController: NavHostController) {
    // Το NavHost είναι το Composable που φιλοξενεί τις οθόνες του γράφου.
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route // Η αρχική οθόνη της εφαρμογής είναι η οθόνη Σύνδεσης.
    ) {
        /**
         * Ορισμός της οθόνης για τη διαδρομή Screen.Login.route.
         */
        composable(route = Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState = viewModel.uiState

            // Το LaunchedEffect παρακολουθεί την κατάσταση uiState.loginSuccess.
            // Όταν γίνει true, εκτελεί τον κώδικα πλοήγησης.
            LaunchedEffect(key1 = uiState.loginSuccess) {
                if (uiState.loginSuccess) {
                    // Πλοήγηση στην κεντρική οθόνη.
                    navController.navigate(Screen.Home.route) {
                        // Η popUpTo αφαιρεί την οθόνη Login από το back stack,
                        // ώστε ο χρήστης να μην μπορεί να γυρίσει πίσω σε αυτήν με το κουμπί back.
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                }
            }

            LoginScreen(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )
        }

        /**
         * Η διαδρομή "home_screen" δεν οδηγεί απευθείας στην HomeScreen, αλλά στο MainScreen Composable.
         * Το MainScreen περιέχει τη δομή με την κάτω μπάρα πλοήγησης (Bottom Navigation Bar)
         * και τον δικό του ένθετο γράφο πλοήγησης (BottomNavGraph).
         */
        composable(route = Screen.Home.route) {
            // Περνάμε το main NavController στο MainScreen ώστε οι οθόνες μέσα σε αυτό
            // (π.χ., η HomeScreen) να μπορούν να πλοηγηθούν σε άλλες οθόνες εκτός της κάτω μπάρας
            // (π.χ., στην οθόνη ProductDetails).
            MainScreen(mainNavController = navController)
        }

        /**
         * Ορισμός της οθόνης για τη διαδρομή λεπτομερειών προϊόντος.
         * @param arguments Ορίζει τα ορίσματα που δέχεται αυτή η διαδρομή. Εδώ, δέχεται ένα
         * 'productId' τύπου Int.
         */
        composable(
            route = Screen.ProductDetails.route,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) {
            val detailsViewModel: ProductDetailsViewModel = hiltViewModel()
            ProductDetailsScreen(
                viewModel = detailsViewModel,
                // Η ενέργεια για την επιστροφή στην προηγούμενη οθόνη.
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}