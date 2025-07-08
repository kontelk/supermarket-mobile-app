// ui/details/ProductDetailsScreen.kt
package com.example.supermarket_mobile_app.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest

/**
 * Η Composable συνάρτηση που σχεδιάζει την οθόνη Λεπτομερειών Προϊόντος.
 * @param viewModel Το ViewModel που παρέχει την κατάσταση (state) για την οθόνη.
 * @param onNavigateBack Μια συνάρτηση που καλείται όταν ο χρήστης πατάει το κουμπί επιστροφής.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel,
    onNavigateBack: () -> Unit
) {
    // Παίρνουμε την τρέχουσα κατάσταση για το προϊόν από το ViewModel.
    val product by viewModel.product.collectAsState()
    // Παίρνουμε την κατάσταση για το αν το προϊόν είναι στη λίστα επιθυμιών.
    val isInWishlist by viewModel.isInWishlist.collectAsState()
    // Δημιουργούμε και θυμόμαστε την κατάσταση για το Snackbar (τα μηνύματα που εμφανίζονται στο κάτω μέρος).
    val snackbarHostState = remember { SnackbarHostState() }

    // Το LaunchedEffect εκτελεί κώδικα που δεν σχετίζεται άμεσα με το UI, όπως η παρατήρηση ενός Flow.
    // Το key1 = true σημαίνει ότι θα εκτελεστεί μόνο μία φορά όταν η οθόνη εμφανιστεί.
    LaunchedEffect(key1 = true) {
        // Παρακολουθούμε τα one-time events από το ViewModel για να εμφανίσουμε μηνύματα.
        viewModel.uiEvents.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    // Το Scaffold παρέχει μια βασική δομή Material Design (TopBar, content, Snackbar, κ.λπ.).
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = product?.name ?: "Φόρτωση...") },
                navigationIcon = {
                    // Κουμπί επιστροφής που καλεί τη συνάρτηση onNavigateBack.
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Πίσω"
                        )
                    }
                },
                actions = {
                    // Κουμπί για τη λίστα επιθυμιών στη δεξιά πλευρά του TopAppBar.
                    IconButton(onClick = { viewModel.onWishlistClick() }) {
                        Icon(
                            // Το εικονίδιο αλλάζει ανάλογα με το αν το προϊόν είναι στη λίστα επιθυμιών.
                            imageVector = if (isInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Προσθήκη στη Λίστα Επιθυμιών"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Έλεγχος αν τα δεδομένα του προϊόντος έχουν φορτωθεί.
        if (product == null) {
            // Αν όχι, εμφανίζουμε μια ένδειξη φόρτωσης στο κέντρο της οθόνης.
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Αν τα δεδομένα έχουν φορτωθεί, εμφανίζουμε τις λεπτομέρειες.
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = product!!.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product!!.description, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Τιμή: €${product!!.price}", style = MaterialTheme.typography.titleLarge)

                // Αυτό το Spacer παίρνει όλο τον υπόλοιπο κάθετο χώρο (λόγω του weight),
                // σπρώχνοντας το κουμπί που ακολουθεί στο κάτω μέρος της οθόνης.
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { viewModel.onAddToCartClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Προσθήκη στο Καλάθι")
                }
            }
        }
    }
}