// ui/home/HomeViewModel.kt
package com.example.supermarket_mobile_app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supermarket_mobile_app.data.Category
import com.example.supermarket_mobile_app.data.Product
import com.example.supermarket_mobile_app.repositories.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Data class που περιγράφει και ενкапсуλώνει ολόκληρη την κατάσταση (state)
 * της Αρχικής Οθόνης.
 * @param categories Η λίστα με τις διαθέσιμες κατηγορίες.
 * @param products Η λίστα με τα προϊόντα (φιλτραρισμένη ή όχι).
 * @param selectedCategoryId Το ID της κατηγορίας που έχει επιλέξει ο χρήστης για φιλτράρισμα.
 * Είναι null αν δεν έχει επιλεγεί κάποιο φίλτρο (επιλογή "Όλα").
 */
data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val products: List<Product> = emptyList(),
    val selectedCategoryId: Int? = null
)

/**
 * Το ViewModel για την Αρχική Οθόνη.
 * Είναι υπεύθυνο για την ανάκτηση των δεδομένων (κατηγορίες, προϊόντα) και τη διαχείριση
 * της λογικής του φιλτραρίσματος.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    // Ένα εσωτερικό, μεταβλητό StateFlow που κρατά το ID της επιλεγμένης κατηγορίας.
    // Είναι private για να μπορεί να αλλάξει μόνο από μέσα από το ViewModel.
    private val _selectedCategoryId = MutableStateFlow<Int?>(null)

    /**
     * Το δημόσιο, μη-μεταβλητό StateFlow που εκθέτει την πλήρη κατάσταση του UI.
     * Το UI παρατηρεί αυτό το state για να ανανεώνεται.
     */
    val uiState: StateFlow<HomeUiState> = combine(
        repository.getAllCategories(), // Flow 1: Η λίστα των κατηγοριών
        repository.getAllProducts(),   // Flow 2: Η λίστα όλων των προϊόντων
        _selectedCategoryId            // Flow 3: Το ID της επιλεγμένης κατηγορίας
    ) { categories, allProducts, selectedId ->
        // Αυτό το μπλοκ κώδικα εκτελείται κάθε φορά που οποιοδήποτε από τα παραπάνω 3 Flows
        // εκπέμπει μια νέα τιμή.

        // Φιλτράρουμε τη λίστα των προϊόντων με βάση το selectedId.
        val filteredProducts = if (selectedId == null) {
            allProducts // Αν δεν υπάρχει επιλεγμένο ID, επιστρέφουμε όλα τα προϊόντα.
        } else {
            allProducts.filter { it.categoryId == selectedId } // Αλλιώς, φιλτράρουμε.
        }

        // Δημιουργούμε και επιστρέφουμε το νέο, ενημερωμένο αντικείμενο UiState.
        HomeUiState(
            categories = categories,
            products = filteredProducts,
            selectedCategoryId = selectedId
        )
    }.stateIn( // Μετατρέπουμε το Flow<HomeUiState> σε StateFlow.
        scope = viewModelScope, // Ο κύκλος ζωής του συνδέεται με αυτόν του ViewModel.
        started = SharingStarted.WhileSubscribed(5000), // Ξεκινά όταν το UI είναι ορατό.
        initialValue = HomeUiState() // Η αρχική τιμή μέχρι να έρθουν τα δεδομένα.
    )


    /**
     * Συνάρτηση που καλείται από το UI όταν ο χρήστης επιλέγει μια κατηγορία.
     * @param categoryId Το ID της κατηγορίας που επιλέχθηκε, ή null για την επιλογή "Όλα".
     */
    fun onCategorySelected(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
    }
}