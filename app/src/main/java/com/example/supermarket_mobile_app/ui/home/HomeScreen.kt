// ui/home/HomeScreen.kt
package com.example.supermarket_mobile_app.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Η Composable συνάρτηση που σχεδιάζει την Αρχική Οθόνη της εφαρμογής.
 * @param viewModel Το ViewModel που παρέχει την κατάσταση (state) για την οθόνη.
 * @param onProductClick Μια συνάρτηση που καλείται όταν ο χρήστης πατάει πάνω σε ένα προϊόν,
 * περνώντας το ID του προϊόντος για πλοήγηση.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onProductClick: (Int) -> Unit
) {
    // Παίρνουμε την τρέχουσα κατάσταση (uiState) από το ViewModel.
    // Η συνάρτηση collectAsState διασφαλίζει ότι το UI θα ανανεωθεί (recompose)
    // κάθε φορά που αλλάζει το uiState στο ViewModel.
    val uiState by viewModel.uiState.collectAsState()

    // Κύριος container της οθόνης, οργανώνει τα στοιχεία κάθετα.
    Column(modifier = Modifier.fillMaxSize()) {
        // Οριζόντια λίστα που σκρολάρει για τα φίλτρα των κατηγοριών.
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp) // Κενό μεταξύ των chips
        ) {
            // Προσθέτουμε ένα σταθερό chip για την επιλογή "Όλα".
            item {
                FilterChip(
                    selected = uiState.selectedCategoryId == null,
                    onClick = { viewModel.onCategorySelected(null) }, // Αφαιρεί το φίλτρο
                    label = { Text("Όλα") }
                )
            }
            // Δημιουργούμε δυναμικά ένα chip για κάθε κατηγορία.
            items(uiState.categories) { category ->
                FilterChip(
                    // Το chip είναι "επιλεγμένο" αν το ID του ταιριάζει με το selectedCategoryId στο state.
                    selected = uiState.selectedCategoryId == category.id,
                    onClick = { viewModel.onCategorySelected(category.id) },
                    label = { Text(category.name) }
                )
            }
        }

        // Κάθετη λίστα που σκρολάρει για την εμφάνιση των προϊόντων.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Κενό μεταξύ των καρτών
        ) {
            items(uiState.products) { product ->
                // Καλούμε το ProductCard composable για κάθε προϊόν, περνώντας τη συνάρτηση για το κλικ.
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product.id) }
                )
            }
        }
    }
}

/**
 * Ένα επαναχρησιμοποιήσιμο Composable που σχεδιάζει την κάρτα για ένα μεμονωμένο προϊόν.
 * @param product Το αντικείμενο Product με τα δεδομένα προς εμφάνιση.
 * @param onClick Η συνάρτηση που θα εκτελεστεί όταν ο χρήστης πατήσει την κάρτα.
 */
@Composable
fun ProductCard(
    product: com.example.supermarket_mobile_app.data.Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick), // Κάνουμε ολόκληρη την κάρτα πατήσιμη.
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = product.description, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "€${product.price}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}