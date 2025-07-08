// repositories/LoginRepository.kt
package com.example.supermarket_mobile_app.repositories

import com.example.supermarket_mobile_app.data.UserDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository που διαχειρίζεται τις λειτουργίες σύνδεσης.
 * Επικοινωνεί με το UserDao για την επαλήθευση του χρήστη.
 * @Singleton: Αυτή η ετικέτα δηλώνει στο Hilt να δημιουργήσει μόνο ένα αντικείμενο (instance)
 * αυτού του repository για ολόκληρη την εφαρμογή.
 */
@Singleton
class LoginRepository @Inject constructor(
    private val userDao: UserDao
) {

    /**
     * Επαληθεύει τα στοιχεία του χρήστη.
     * @param username Το όνομα χρήστη που δόθηκε.
     * @param password Ο κωδικός που δόθηκε.
     * @return Επιστρέφει true αν τα στοιχεία είναι σωστά, αλλιώς false.
     */
    suspend fun validateUser(username: String, password: String): Boolean {
        // Βρίσκει τον χρήστη στη βάση δεδομένων με βάση το παρεχόμενο username.
        val user = userDao.findByUsername(username)
        // Ελέγχει αν ο χρήστης βρέθηκε (δεν είναι null) και αν ο παρεχόμενος κωδικός
        // ταιριάζει με τον κωδικό που είναι αποθηκευμένος στη βάση δεδομένων για αυτόν τον χρήστη.
        return user != null && user.password == password
    }
}