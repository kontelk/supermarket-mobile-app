// ui/MainScreen.kt
package com.example.supermarket_mobile_app.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.supermarket_mobile_app.ui.navigation.BottomNavGraph
import com.example.supermarket_mobile_app.ui.navigation.BottomNavItem

/**
 * Η Composable συνάρτηση για την Κύρια Οθόνη.
 * Αυτή η οθόνη λειτουργεί ως "κέλυφος" που περιέχει την κάτω μπάρα πλοήγησης
 * και τον ένθετο γράφο πλοήγησης (BottomNavGraph) για τις καρτέλες.
 * @param mainNavController Ο κεντρικός NavController της εφαρμογής, που χρησιμοποιείται για
 * πλοήγηση σε οθόνες εκτός της κάτω μπάρας (π.χ. Λεπτομέρειες Προϊόντος).
 */
@Composable
fun MainScreen(mainNavController: NavHostController) {
    // Δημιουργούμε έναν ξεχωριστό NavController που θα ελέγχει αποκλειστικά
    // την πλοήγηση μεταξύ των καρτελών της κάτω μπάρας.
    val bottomNavController = rememberNavController()

    // Το Scaffold παρέχει τη βασική δομή για την οθόνη.
    Scaffold(
        // Ορίζουμε το περιεχόμενο της κάτω μπάρας.
        bottomBar = { BottomBar(navController = bottomNavController) }
    ) { paddingValues ->
        // Στο κυρίως σώμα του Scaffold, τοποθετούμε τον ένθετο γράφο πλοήγησης.
        // Του περνάμε και τους δύο NavControllers, καθώς και το padding από το Scaffold.
        BottomNavGraph(
            mainNavController = mainNavController,
            bottomNavController = bottomNavController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * Η Composable συνάρτηση που δημιουργεί την κάτω μπάρα πλοήγησης.
 * @param navController Ο NavController που ελέγχει τις καρτέλες της μπάρας.
 */
@Composable
fun BottomBar(navController: NavHostController) {
    // Η λίστα με τα στοιχεία που θα εμφανιστούν στην μπάρα.
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Cart,
    )
    // Παίρνουμε την τρέχουσα κατάσταση πλοήγησης για να ξέρουμε ποια καρτέλα είναι ενεργή.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Το Composable που σχεδιάζει την μπάρα.
    NavigationBar {
        // Δημιουργούμε ένα κουμπί (NavigationBarItem) για κάθε στοιχείο στη λίστα μας.
        screens.forEach { screen ->
            NavigationBarItem(
                label = { Text(text = screen.label) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.label) },
                // Ελέγχουμε αν το τρέχον route ανήκει στην ιεραρχία του route του κουμπιού.
                // Αυτό διασφαλίζει ότι το κουμπί παραμένει επιλεγμένο ακόμα και σε ένθετες οθόνες.
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                // Η λογική που εκτελείται όταν πατιέται ένα κουμπί.
                onClick = {
                    navController.navigate(screen.route) {
                        // Επιστρέφει στην αρχική οθόνη του γράφου για να αποφύγει τη δημιουργία
                        // μιας μεγάλης στοίβας (back stack) από οθόνες.
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // Αποθηκεύει την κατάσταση της οθόνης από την οποία φεύγουμε.
                        }
                        // Αποφεύγει τη δημιουργία πολλαπλών αντιγράφων της ίδιας οθόνης στη στοίβα.
                        launchSingleTop = true
                        // Επαναφέρει την κατάσταση αν επιστρέψουμε σε μια οθόνη που έχουμε ήδη επισκεφθεί.
                        restoreState = true
                    }
                }
            )
        }
    }
}