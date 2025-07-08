// data/Product.kt
package com.example.supermarket_mobile_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Αυτή η κλάση αναπαριστά την οντότητα ενός προϊόντος στη βάση δεδομένων.
 * @param tableName Το όνομα του πίνακα που θα δημιουργηθεί στη βάση.
 * @param foreignKeys Ορίζει μια σχέση ξένου κλειδιού (Foreign Key) με τον πίνακα 'categories'.
 */
@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        entity = Category::class, // Ο "γονικός" πίνακας της σχέσης.
        parentColumns = ["id"],   // Η στήλη-κλειδί στον γονικό πίνακα.
        childColumns = ["category_id"], // Η στήλη-κλειδί σε αυτόν τον πίνακα (τον "παιδικό").
        onDelete = ForeignKey.CASCADE // Σημαντικό: Αν διαγραφεί μια κατηγορία, θα διαγραφούν αυτόματα και όλα τα προϊόντα που ανήκουν σε αυτή.
    )]
)
data class Product(
    /**
     * Το πρωτεύον κλειδί (Primary Key) του πίνακα.
     * Το Room θα του δίνει αυτόματα μια μοναδική, αυξανόμενη τιμή.
     */
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * Το όνομα του προϊόντος (π.χ., "Γάλα Φρέσκο").
     */
    val name: String,

    /**
     * Η περιγραφή του προϊόντος (π.χ., "1.5L Πλήρες").
     */
    val description: String,

    /**
     * Η τοποθεσία (URL ή τοπικό path) της εικόνας του προϊόντος.
     * Η ετικέτα @ColumnInfo μας επιτρέπει να ορίσουμε ένα διαφορετικό όνομα για τη στήλη στη βάση
     * ("image_url") από αυτό της μεταβλητής στην κλάση ("imageUrl").
     */
    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    /**
     * Η τιμή του προϊόντος.
     */
    val price: Double,

    /**
     * Το ξένο κλειδί που συνδέει αυτό το προϊόν με μια εγγραφή στον πίνακα 'categories'.
     * Η τιμή του πρέπει να αντιστοιχεί στο 'id' μιας υπάρχουσας κατηγορίας.
     */
    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    /**
     * Μια σημαία (flag) που δείχνει αν το προϊόν είναι σε προσφορά.
     * Έχει προεπιλεγμένη τιμή 'false'.
     */
    @ColumnInfo(name = "on_offer")
    val onOffer: Boolean = false,
)