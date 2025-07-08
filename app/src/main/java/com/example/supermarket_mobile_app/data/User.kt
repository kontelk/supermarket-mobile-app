// data/User.kt
package com.example.supermarket_mobile_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Αυτή η κλάση αναπαριστά την οντότητα ενός χρήστη στην τοπική βάση δεδομένων.
 * Η ετικέτα @Entity δηλώνει ότι αυτή η data class αντιστοιχεί σε έναν πίνακα στη βάση.
 * @param tableName Το όνομα του πίνακα που θα δημιουργηθεί, σε αυτή την περίπτωση "users".
 */
@Entity(tableName = "users")
data class User(
    /**
     * Το πρωτεύον κλειδί (Primary Key) του πίνακα.
     * Η ετικέτα @PrimaryKey το ορίζει ως το μοναδικό αναγνωριστικό για κάθε εγγραφή.
     * Η ιδιότητα autoGenerate = true αναθέτει στο Room τη δημιουργία ενός μοναδικού,
     * αυξανόμενου αριθμού για κάθε νέο χρήστη.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Το όνομα χρήστη (username) για τη σύνδεση.
     * Η ετικέτα @ColumnInfo ορίζει το όνομα της στήλης στον πίνακα της βάσης.
     * Εδώ, το όνομα της στήλης ("username") είναι ίδιο με το όνομα της μεταβλητής.
     */
    @ColumnInfo(name = "username")
    val username: String,

    /**
     * Ο κωδικός πρόσβασης (password) του χρήστη.
     */
    @ColumnInfo(name = "password")
    val password: String
)