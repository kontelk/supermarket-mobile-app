// ui/cart/CartScreen.kt
package com.example.supermarket_mobile_app.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * Η Composable συνάρτηση που σχεδιάζει την οθόνη του Καλαθιού Αγορών.
 * @param viewModel Το ViewModel που παρέχει την κατάσταση (state) για το καλάθι.
 */
@Composable
fun CartScreen(viewModel: CartViewModel) {
    // Παίρνουμε την τρέχουσα κατάσταση (uiState) από το ViewModel.
    // Η συνάρτηση collectAsState διασφαλίζει ότι το UI θα ανανεωθεί (recompose)
    // κάθε φορά που αλλάζει το uiState στο ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // Κύριος container της οθόνης, οργανώνει τα στοιχεία κάθετα.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Τίτλος της οθόνης.
        Text("Το Καλάθι μου", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Ελέγχουμε αν το καλάθι είναι άδειο.
        if (uiState.items.isEmpty()) {
            Text("Το καλάθι σας είναι άδειο.")
        } else {
            // Δημιουργούμε μια λίστα που σκρολάρει, για την εμφάνιση των προϊόντων.
            // Το weight(1f) κάνει τη LazyColumn να πιάνει όλο τον διαθέσιμο χώρο,
            // σπρώχνοντας το συνολικό κόστος στο κάτω μέρος.
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(uiState.items) { item ->
                    // Κάθε αντικείμενο στη λίστα.
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Εμφανίζει την ποσότητα και το όνομα του προϊόντος.
                            Text(
                                text = "${item.quantity}x ${item.name}",
                                modifier = Modifier.weight(1f), // Δίνουμε ευελιξία στο πλάτος
                                style = MaterialTheme.typography.bodyLarge
                            )
                            // Εμφανίζει το συνολικό κόστος για τη συγκεκριμένη γραμμή (τιμή * ποσότητα).
                            // Το "%.2f" μορφοποιεί τον αριθμό ώστε να έχει πάντα δύο δεκαδικά ψηφία.
                            Text(
                                text = "€${"%.2f".format(item.price * item.quantity)}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    // Μια γραμμή διαχωρισμού μεταξύ των αντικειμένων.
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Εμφανίζει το τελικό συνολικό κόστος όλων των προϊόντων.
            Text(
                "Συνολικό Κόστος: €${"%.2f".format(uiState.totalPrice)}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}