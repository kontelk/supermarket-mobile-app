// ui/details/ProductDetailsViewModel.kt
package com.example.supermarket_mobile_app.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supermarket_mobile_app.data.Product
import com.example.supermarket_mobile_app.repositories.CartRepository
import com.example.supermarket_mobile_app.repositories.HomeRepository
import com.example.supermarket_mobile_app.repositories.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Το ViewModel για την οθόνη Λεπτομερειών Προϊόντος.
 * Διαχειρίζεται τη λογική για την ανάκτηση των δεδομένων ενός προϊόντος
 * και τις ενέργειες του χρήστη (προσθήκη στο καλάθι/λίστα επιθυμιών).
 */
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    repository: HomeRepository,
    private val cartRepository: CartRepository,
    private val wishlistRepository: WishlistRepository, // Το Hilt παρέχει αυτόματα τα repositories.
    savedStateHandle: SavedStateHandle // Παρέχει πρόσβαση στα ορίσματα πλοήγησης.
) : ViewModel() {

    // Ανακτούμε το 'productId' που περάστηκε μέσω της πλοήγησης.
    // Το checkNotNull διασφαλίζει ότι η τιμή δεν είναι null.
    private val productId: Int = checkNotNull(savedStateHandle["productId"])

    /**
     * StateFlow που κρατά τις λεπτομέρειες του τρέχοντος προϊόντος.
     * Το UI παρατηρεί αυτό το Flow για να εμφανίσει τα δεδομένα.
     */
    val product: StateFlow<Product?> = repository.getProductById(productId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null // Η αρχική τιμή είναι null μέχρι να φορτωθούν τα δεδομένα.
        )

    /**
     * StateFlow που παρακολουθεί αν το τρέχον προϊόν βρίσκεται στη λίστα επιθυμιών του χρήστη.
     * Η τιμή του είναι true αν το προϊόν υπάρχει στη λίστα, αλλιώς false.
     */
    val isInWishlist: StateFlow<Boolean> =
        wishlistRepository.isProductInWishlist(userId = 1, productId = productId)
            .map { it != null } // Μετατρέπουμε το αποτέλεσμα (WishlistItem? ή null) σε Boolean.
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    /**
     * SharedFlow που χρησιμοποιείται για την αποστολή εφάπαξ συμβάντων (one-time events) προς το UI,
     * όπως η εμφάνιση ενός μηνύματος Snackbar.
     */
    private val _uiEvents = MutableSharedFlow<String>()
    val uiEvents = _uiEvents.asSharedFlow()

    /**
     * Διαχειρίζεται τη λογική για το κλικ στο κουμπί "Προσθήκη στο Καλάθι".
     */
    fun onAddToCartClick() {
        viewModelScope.launch {
            // Σημείωση: Χρησιμοποιούμε hardcoded userId = 1 για τον demo χρήστη.
            cartRepository.addProductToCart(userId = 1, productId = productId)
            // Στέλνουμε ένα μήνυμα στο UI για να εμφανιστεί.
            _uiEvents.emit("Το προϊόν προστέθηκε στο καλάθι!")
        }
    }

    /**
     * Διαχειρίζεται τη λογική για το κλικ στο εικονίδιο της λίστας επιθυμιών.
     * Εναλλάσσει την κατάσταση του προϊόντος στη λίστα επιθυμιών (προσθήκη/αφαίρεση).
     */
    fun onWishlistClick() {
        viewModelScope.launch {
            if (isInWishlist.value) {
                wishlistRepository.removeFromWishlist(userId = 1, productId = productId)
                _uiEvents.emit("Το προϊόν αφαιρέθηκε από τη λίστα επιθυμιών")
            } else {
                wishlistRepository.addToWishlist(userId = 1, productId = productId)
                _uiEvents.emit("Το προϊόν προστέθηκε στη λίστα επιθυμιών")
            }
        }
    }
}