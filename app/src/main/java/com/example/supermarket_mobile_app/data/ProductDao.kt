// data/ProductDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) για την οντότητα Product.
 * Παρέχει τις μεθόδους για την αλληλεπίδραση με τον πίνακα 'products' στη βάση δεδομένων.
 */
@Dao
interface ProductDao {

    /**
     * Εισάγει ένα νέο προϊόν στον πίνακα.
     * Αν ένα προϊόν με το ίδιο ID υπάρχει ήδη, θα αντικατασταθεί.
     * Η συνάρτηση είναι suspend για να εκτελείται ασύγχρονα.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    /**
     * Επιστρέφει όλα τα προϊόντα από τον πίνακα.
     * @return Ένα Flow που εκπέμπει τη λίστα των προϊόντων. Το UI θα ενημερώνεται
     * αυτόματα κάθε φορά που αλλάζουν τα δεδομένα των προϊόντων.
     */
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    /**
     * Επιστρέφει όλα τα προϊόντα που ανήκουν σε μια συγκεκριμένη κατηγορία.
     * @param categoryId Το ID της κατηγορίας για την οποία θέλουμε τα προϊόντα.
     * @return Ένα Flow που εκπέμπει τη λίστα των φιλτραρισμένων προϊόντων.
     */
    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    fun getProductsByCategory(categoryId: Int): Flow<List<Product>>

    /**
     * Επιστρέφει ένα συγκεκριμένο προϊόν με βάση το ID του.
     * @param productId Το μοναδικό ID του προϊόντος.
     * @return Ένα Flow που εκπέμπει το προϊόν. Μπορεί να εκπέμψει null αν δεν βρεθεί προϊόν με αυτό το ID.
     */
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: Int): Flow<Product?>
}