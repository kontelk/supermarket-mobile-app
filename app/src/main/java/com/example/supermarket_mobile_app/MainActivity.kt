// MainActivity.kt
package com.example.supermarket_mobile_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.supermarket_mobile_app.ui.navigation.NavGraph
import com.example.supermarket_mobile_app.ui.theme.SupermarketmobileappTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Η ετικέτα @AndroidEntryPoint είναι απαραίτητη για το Hilt.
 * Δηλώνει ότι το Hilt μπορεί να κάνει inject εξαρτήσεις (dependencies)
 * σε αυτή την Android συνιστώσα (Activity).
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Η μεταβλητή που θα κρατά τον κεντρικό ελεγκτή πλοήγησης (NavController).
     * Είναι lateinit γιατί θα αρχικοποιηθεί μέσα στο setContent.
     */
    private lateinit var navController: NavHostController

    /**
     * Η μέθοδος onCreate() καλείται όταν η Activity δημιουργείται για πρώτη φορά.
     * Είναι το σημείο εκκίνησης για τη ρύθμιση του UI.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Μια βοηθητική συνάρτηση που επιτρέπει στην εφαρμογή να σχεδιάζεται
        // κάτω από τις μπάρες του συστήματος (status bar, navigation bar) για μια
        // πιο μοντέρνα, full-screen εμφάνιση.
        enableEdgeToEdge()

        // Η setContent είναι η "γέφυρα" μεταξύ του κλασικού Android View system και του Jetpack Compose.
        // Οτιδήποτε ορίζεται μέσα σε αυτό το μπλοκ θα σχεδιαστεί χρησιμοποιώντας Compose.
        setContent {
            // Εφαρμόζουμε το προσαρμοσμένο μας θέμα (χρώματα, τυπογραφία)
            // σε ολόκληρη την εφαρμογή.
            SupermarketmobileappTheme {
                // Δημιουργούμε και "θυμόμαστε" (remember) μια κατάσταση για τον NavController.
                // Αυτός ο controller είναι υπεύθυνος για τη διαχείριση της στοίβας πλοήγησης (back stack)
                // και την εναλλαγή μεταξύ των οθονών.
                navController = rememberNavController()

                // Καλούμε το κεντρικό μας NavGraph Composable, περνώντας του τον controller.
                // Το NavGraph περιέχει όλη τη λογική της πλοήγησης της εφαρμογής.
                NavGraph(navController = navController)
            }
        }
    }
}