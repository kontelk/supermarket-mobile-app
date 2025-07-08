// data/ShoppingListItem.kt
package com.example.supermarket_mobile_app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * Αυτή η κλάση αναπαριστά ένα αντικείμενο (item) μέσα σε μια λίστα αγορών.
 * Λειτουργεί ως ένας "συνδετικός πίνακας" (junction table) που συνδέει τον πίνακα
 * `shopping_lists` με τον πίνακα `products`.
 *
 * @param tableName Το όνομα του πίνακα στη βάση.
 * @param primaryKeys Ορίζει ένα "σύνθετο πρωτεύον κλειδί". Αυτό σημαίνει ότι ο συνδυασμός
 * των τιμών `list_id` και `product_id` πρέπει να είναι μοναδικός. Δεν μπορεί δηλαδή
 * το ίδιο προϊόν να υπάρχει δύο φορές (ως δύο διαφορετικές εγγραφές) στην ίδια λίστα.
 * @param foreignKeys Ορίζει τα δύο ξένα κλειδιά του πίνακα.
 */
@Entity(
    tableName = "shopping_list_items",
    primaryKeys = ["list_id", "product_id"],
    foreignKeys = [
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = ["id"],
            childColumns = ["list_id"],
            onDelete = ForeignKey.CASCADE // Αν διαγραφεί μια λίστα, διαγράφονται και όλα τα items της.
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE // Αν ένα προϊόν διαγραφεί (π.χ. από τον κατάλογο), αφαιρείται και από όλες τις λίστες.
        )
    ]
)
data class ShoppingListItem(
    /**
     * Το ID της λίστας αγορών στην οποία ανήκει αυτό το αντικείμενο.
     * Είναι μέρος του σύνθετου πρωτεύοντος κλειδιού και ξένο κλειδί προς τον πίνακα 'shopping_lists'.
     */
    @ColumnInfo(name = "list_id")
    val listId: Int,

    /**
     * Το ID του προϊόντος που αντιστοιχεί σε αυτό το αντικείμενο.
     * Είναι μέρος του σύνθετου πρωτεύοντος κλειδιού και ξένο κλειδί προς τον πίνακα 'products'.
     */
    @ColumnInfo(name = "product_id")
    val productId: Int,

    /**
     * Η ποσότητα του συγκεκριμένου προϊόντος για τη συγκεκριμένη λίστα.
     */
    val quantity: Int
)