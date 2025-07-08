// data/CategoryDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) για την οντότητα Category.
 * Η ετικέτα @Dao δηλώνει στο Room ότι αυτό το interface περιέχει μεθόδους
 * για την πρόσβαση στη βάση δεδομένων.
 */
@Dao
interface CategoryDao {

    /**
     * Εισάγει μια νέα κατηγορία στον πίνακα 'categories'.
     * @param onConflict = OnConflictStrategy.REPLACE: Αν προσπαθήσουμε να εισάγουμε μια κατηγορία
     * με ένα ID που ήδη υπάρχει, η παλιά εγγραφή θα αντικατασταθεί με τη νέα.
     * Η συνάρτηση είναι suspend, που σημαίνει ότι πρέπει να κληθεί από ένα coroutine scope
     * για να εκτελεστεί σε ένα background thread.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    /**
     * Επιστρέφει όλες τις κατηγορίες από τον πίνακα, ταξινομημένες αλφαβητικά
     * με βάση το όνομά τους.
     * @return Επιστρέφει ένα Flow που περιέχει τη λίστα με όλες τις κατηγορίες.
     * Η χρήση του Flow σημαίνει ότι η λίστα θα εκπέμπεται αυτόματα κάθε φορά
     * που τα δεδομένα στον πίνακα 'categories' αλλάζουν, κάνοντας το UI αντιδραστικό (reactive).
     */
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<Category>>
}