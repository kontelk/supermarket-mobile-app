// data/CartItemDetails.kt
package com.example.supermarket_mobile_app.data

/**
 * Μια data class που δεν αντιστοιχεί σε πίνακα (δεν είναι @Entity).
 * Χρησιμοποιείται για να κρατά τα συνδυασμένα αποτελέσματα από ένα JOIN query
 * μεταξύ του πίνακα `products` και του πίνακα `shopping_list_items`.
 * Μας επιτρέπει να έχουμε όλες τις απαραίτητες πληροφορίες για την εμφάνιση
 * ενός αντικειμένου στο καλάθι, σε μία μόνο κλάση.
 */
data class CartItemDetails(
    // Το ID του προϊόντος.
    val productId: Int,

    // Το όνομα του προϊόντος.
    val name: String,

    // Η τιμή του προϊόντος.
    val price: Double,

    // Η ποσότητα του προϊόντος που έχει προστεθεί στο καλάθι.
    val quantity: Int
)