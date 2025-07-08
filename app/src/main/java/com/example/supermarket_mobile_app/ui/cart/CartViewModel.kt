// ui/cart/CartViewModel.kt
package com.example.supermarket_mobile_app.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supermarket_mobile_app.data.CartItemDetails
import com.example.supermarket_mobile_app.repositories.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Data class που αναπαριστά την πλήρη κατάσταση (state) της οθόνης του καλαθιού.
 * @param items Η λίστα με τα προϊόντα και τις λεπτομέρειές τους στο καλάθι.
 * @param totalPrice Το συνολικό κόστος όλων των προϊόντων στο καλάθι.
 */
data class CartUiState(
    val items: List<CartItemDetails> = emptyList(),
    val totalPrice: Double = 0.0
)

/**
 * Το ViewModel για την οθόνη του Καλαθιού Αγορών.
 * Είναι υπεύθυνο για τη λήψη των δεδομένων του καλαθιού και τον υπολογισμό του συνολικού κόστους.
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    cartRepository: CartRepository  // Το Hilt παρέχει αυτόματα το CartRepository.
) : ViewModel() {

    /**
     * Το StateFlow που κρατά την τρέχουσα κατάσταση του UI (CartUiState).
     * Το UI παρατηρεί (observes) αυτό το state για να ενημερώνεται αυτόματα.
     */
    val uiState: StateFlow<CartUiState> =
        cartRepository.getCartItems(1)
            // Χρησιμοποιούμε τη συνάρτηση map για να μετατρέψουμε τη λίστα των CartItemDetails
            // σε ένα αντικείμενο CartUiState.
            .map { items ->
                CartUiState(
                    items = items,
                    // Υπολογίζουμε το συνολικό κόστος πολλαπλασιάζοντας την τιμή κάθε προϊόντος
                    // με την ποσότητά του και αθροίζοντας τα αποτελέσματα.
                    totalPrice = items.sumOf { it.price * it.quantity }
                )
            }
            // Μετατρέπουμε το Flow σε StateFlow, το οποίο είναι ιδανικό για να κρατά κατάσταση για το UI.
            .stateIn(
                scope = viewModelScope,    // Ο κύκλος ζωής του Flow συνδέεται με αυτόν του ViewModel.
                started = SharingStarted.WhileSubscribed(5000), // Το Flow ξεκινά όταν το UI γίνεται ορατό.
                initialValue = CartUiState()   // Η αρχική κατάσταση είναι ένα άδειο καλάθι.
            )
}
