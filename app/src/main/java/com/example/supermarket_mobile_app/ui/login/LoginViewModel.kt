// ui/login/LoginViewModel.kt
package com.example.supermarket_mobile_app.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supermarket_mobile_app.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data class που αναπαριστά την πλήρη κατάσταση (state) της οθόνης σύνδεσης.
 * @param username Το κείμενο στο πεδίο του ονόματος χρήστη.
 * @param password Το κείμενο στο πεδίο του κωδικού.
 * @param isLoading Μια σημαία (flag) που δείχνει αν εκτελείται κάποια ασύγχρονη διαδικασία (π.χ. έλεγχος στοιχείων).
 * @param error Ένα μήνυμα σφάλματος προς εμφάνιση, ή null αν δεν υπάρχει σφάλμα.
 * @param loginSuccess Μια σημαία που γίνεται true όταν η σύνδεση είναι επιτυχής, για να ενεργοποιήσει την πλοήγηση.
 */
data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val loginSuccess: Boolean = false
)

/**
 * Sealed interface που ορίζει όλα τα πιθανά συμβάντα (events) που μπορεί να στείλει το UI στο ViewModel.
 * Η χρήση sealed interface κάνει τον χειρισμό των events πιο ασφαλή και προβλέψιμο.
 */
sealed interface LoginEvent {
    data class UsernameChanged(val username: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    object LoginButtonPressed : LoginEvent
}

/**
 * Το ViewModel για την οθόνη Σύνδεσης.
 * Διαχειρίζεται την κατάσταση του UI και τη λογική πίσω από τις ενέργειες του χρήστη.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository // Το Hilt παρέχει αυτόματα το LoginRepository.
) : ViewModel() {

    /**
     * Η μεταβλητή που κρατά την τρέχουσα κατάσταση του UI.
     * Είναι τύπου mutableStateOf ώστε το Compose να την παρατηρεί για αλλαγές.
     * Το 'private set' σημαίνει ότι μόνο το ViewModel μπορεί να τροποποιήσει το state,
     * ακολουθώντας την αρχή του Unidirectional Data Flow.
     */
    var uiState by mutableStateOf(LoginUiState())
        private set

    /**
     * Η κεντρική συνάρτηση που δέχεται τα events από το UI.
     * Χρησιμοποιεί ένα when statement για να καλέσει την κατάλληλη λογική.
     */
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                // Ενημερώνει το state με το νέο username.
                uiState = uiState.copy(username = event.username)
            }
            is LoginEvent.PasswordChanged -> {
                // Ενημερώνει το state με τον νέο κωδικό.
                uiState = uiState.copy(password = event.password)
            }
            LoginEvent.LoginButtonPressed -> {
                // Καλεί τη συνάρτηση για την εκτέλεση της λογικής σύνδεσης.
                loginUser()
            }
        }
    }

    /**
     * Private συνάρτηση που περιέχει τη λογική για την επαλήθευση των στοιχείων του χρήστη.
     */
    private fun loginUser() {
        // Θέτουμε την κατάσταση σε "φορτώνει" για να δείξουμε στο UI ένα loading indicator.
        uiState = uiState.copy(isLoading = true, error = null)

        // Εκκινούμε μια coroutine που θα εκτελεστεί σε background thread.
        viewModelScope.launch {
            val isValid = repository.validateUser(uiState.username, uiState.password)
            if (isValid) {
                // Αν τα στοιχεία είναι σωστά, ενημερώνουμε το state για επιτυχή σύνδεση.
                uiState = uiState.copy(isLoading = false, loginSuccess = true)
            } else {
                // Αν τα στοιχεία είναι λάθος, ενημερώνουμε το state με ένα μήνυμα σφάλματος.
                uiState = uiState.copy(
                    isLoading = false,
                    error = "Λάθος όνομα χρήστη ή κωδικός."
                )
            }
        }
    }
}