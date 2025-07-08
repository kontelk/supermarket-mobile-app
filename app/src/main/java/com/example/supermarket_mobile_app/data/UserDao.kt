// data/UserDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) για την οντότητα User.
 * Η ετικέτα @Dao δηλώνει στο Room ότι αυτό το interface περιέχει μεθόδους
 * για την πρόσβαση στη βάση δεδομένων που αφορούν τον χρήστη.
 */
@Dao
interface UserDao {

    /**
     * Εισάγει έναν χρήστη στον πίνακα.
     * @param onConflict = OnConflictStrategy.REPLACE: Αν ο χρήστης υπάρχει ήδη (βάσει του id),
     * η παλιά εγγραφή θα αντικατασταθεί με την καινούργια.
     * Η συνάρτηση είναι suspend, που σημαίνει ότι πρέπει να κληθεί από ένα coroutine scope
     * για να εκτελεστεί σε ένα background thread.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    /**
     * Βρίσκει έναν χρήστη με βάση το username του.
     * @param username Το όνομα χρήστη που αναζητούμε.
     * @return Επιστρέφει ένα αντικείμενο User αν βρεθεί, ή null αν δεν υπάρχει χρήστης
     * με αυτό το username. Η συνάρτηση είναι suspend καθώς είναι μια εφάπαξ
     * ανάγνωση από τη βάση δεδομένων.
     */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun findByUsername(username: String): User?
}