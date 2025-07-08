// data/ShoppingList.kt
package com.example.supermarket_mobile_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Αυτή η κλάση αναπαριστά την οντότητα μιας λίστας αγορών στη βάση δεδομένων.
 * Κάθε εγγραφή αντιστοιχεί σε μια ξεχωριστή λίστα (π.χ., "Αγορές 15/07/2025").
 * @param tableName Το όνομα του πίνακα που θα δημιουργηθεί στη βάση.
 * @param foreignKeys Ορίζει μια σχέση ξένου κλειδιού με τον πίνακα 'users'.
 */
@Entity(
    tableName = "shopping_lists",
    foreignKeys = [ForeignKey(
        entity = User::class,     // Ο "γονικός" πίνακας της σχέσης.
        parentColumns = ["id"],   // Η στήλη-κλειδί στον γονικό πίνακα.
        childColumns = ["user_id"], // Η στήλη-κλειδί σε αυτόν τον πίνακα.
        onDelete = ForeignKey.CASCADE // Αν διαγραφεί ένας χρήστης, διαγράφονται και οι λίστες του.
    )]
)
data class ShoppingList(
    /**
     * Το πρωτεύον κλειδί (Primary Key) του πίνακα.
     * Το Room θα του δίνει αυτόματα μια μοναδική, αυξανόμενη τιμή.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Το ξένο κλειδί που συνδέει αυτή τη λίστα με έναν χρήστη.
     * Η τιμή του πρέπει να αντιστοιχεί στο 'id' ενός υπάρχοντος χρήστη.
     */
    @ColumnInfo(name = "user_id")
    val userId: Int,

    /**
     * Η ημερομηνία και ώρα δημιουργίας της λίστας.
     * Το Room χρησιμοποιεί τον TypeConverter που ορίσαμε για να αποθηκεύσει αυτό το πεδίο.
     */
    @ColumnInfo(name = "creation_date")
    val creationDate: Date,

    /**
     * Η κατάσταση της λίστας.
     * Θα χρησιμοποιείται για να διακρίνουμε την ενεργή λίστα ("active")
     * από τις ολοκληρωμένες ("completed") που θα ανήκουν στο ιστορικό αγορών.
     */
    val status: String
)