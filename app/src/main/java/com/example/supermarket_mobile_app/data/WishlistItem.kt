// data/WishlistItem.kt
package com.example.supermarket_mobile_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Αυτή η κλάση αναπαριστά μια μεμονωμένη εγγραφή προϊόντος στη λίστα επιθυμιών ενός χρήστη.
 * Λειτουργεί ως ένας "συνδετικός πίνακας" (linking table) μεταξύ των πινάκων 'users' και 'products'.
 *
 * @param tableName Το όνομα του πίνακα που θα δημιουργηθεί στη βάση δεδομένων.
 * @param primaryKeys Ορίζει ένα "σύνθετο πρωτεύον κλειδί". Αυτό σημαίνει ότι ο συνδυασμός
 * των 'user_id' και 'product_id' πρέπει να είναι μοναδικός. Ένας χρήστης δεν μπορεί να προσθέσει
 * το ίδιο προϊόν στη λίστα επιθυμιών του περισσότερες από μία φορές.
 * @param foreignKeys Ορίζει τα δύο ξένα κλειδιά για τον πίνακα.
 */
@Entity(
    tableName = "wishlist_items",
    primaryKeys = ["user_id", "product_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE // Αν διαγραφεί ένας χρήστης, διαγράφονται και τα αντικείμενα της λίστας επιθυμιών του.
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE // Αν ένα προϊόν διαγραφεί, αφαιρείται από όλες τις λίστες επιθυμιών.
        )
    ]
)
data class WishlistItem(
    /**
     * Το ID του χρήστη στον οποίο ανήκει αυτή η εγγραφή.
     * Αποτελεί μέρος του σύνθετου πρωτεύοντος κλειδιού και είναι ξένο κλειδί προς τον πίνακα 'users'.
     */
    @ColumnInfo(name = "user_id")
    val userId: Int,

    /**
     * Το ID του προϊόντος που έχει προστεθεί στη λίστα επιθυμιών.
     * Αποτελεί μέρος του σύνθετου πρωτεύοντος κλειδιού και είναι ξένο κλειδί προς τον πίνακα 'products'.
     */
    @ColumnInfo(name = "product_id")
    val productId: Int
)