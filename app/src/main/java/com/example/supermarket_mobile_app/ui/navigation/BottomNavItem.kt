// ui/navigation/BottomNavItem.kt
package com.example.supermarket_mobile_app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Μια "σφραγισμένη" κλάση (sealed class) που αναπαριστά ένα στοιχείο
 * στην κάτω μπάρα πλοήγησης (Bottom Navigation Bar).
 * Η χρήση sealed class μας επιτρέπει να ορίσουμε ένα περιορισμένο σύνολο από
 * πιθανές οθόνες για την πλοήγηση, κάνοντας τον κώδικα πιο ασφαλή.
 *
 * @param route Η μοναδική διαδρομή (route) για την πλοήγηση.
 * @param icon Το εικονίδιο που θα εμφανίζεται στην μπάρα.
 * @param label Η ετικέτα κειμένου που θα εμφανίζεται κάτω από το εικονίδιο.
 */
sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    /**
     * Αντιπροσωπεύει την καρτέλα "Αρχική".
     */
    object Home : BottomNavItem("home", Icons.Default.Home, "Αρχική")

    /**
     * Αντιπροσωπεύει την καρτέλα "Καλάθι".
     */
    object Cart : BottomNavItem("cart", Icons.Default.ShoppingCart, "Καλάθι")

    // Σημείωση: Αν στο μέλλον θέλουμε να προσθέσουμε κι άλλες καρτέλες,
    // όπως "Λίστα Επιθυμιών" ή "Ιστορικό", απλά θα προσθέσουμε ένα νέο object εδώ.
    // object Wishlist : BottomNavItem("wishlist", Icons.Default.Favorite, "Επιθυμητά")
}