// data/Category.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Αυτή η κλάση αναπαριστά την οντότητα μιας κατηγορίας προϊόντων στη βάση δεδομένων.
 * Η ετικέτα @Entity δηλώνει ότι αυτή η data class αντιστοιχεί σε έναν πίνακα στη βάση.
 * @param tableName Το όνομα του πίνακα που θα δημιουργηθεί στη βάση δεδομένων.
 */
@Entity(tableName = "categories")
data class Category(
    /**
     * Το πρωτεύον κλειδί (Primary Key) του πίνακα.
     * Η ετικέτα @PrimaryKey το ορίζει ως τέτοιο.
     * Η ιδιότητα autoGenerate = true σημαίνει ότι η βάση δεδομένων θα αναλάβει
     * να δίνει αυτόματα μια μοναδική, αυξανόμενη τιμή (ID) σε κάθε νέα κατηγορία.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Το όνομα της κατηγορίας (π.χ., "Γαλακτοκομικά", "Φρούτα & Λαχανικά").
     * Αυτό θα αποθηκευτεί σε μια στήλη με όνομα "name".
     */
    val name: String
)