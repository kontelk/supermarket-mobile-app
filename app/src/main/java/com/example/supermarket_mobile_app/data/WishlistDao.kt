// data/WishlistDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the WishlistItem entity.
 * Defines the database access methods for the 'wishlist_items' table.
 */
@Dao
interface WishlistDao {

    /**
     * Inserts a new item into the wishlist.
     * @param onConflict = OnConflictStrategy.IGNORE: If an item with the same
     * composite primary key (userId, productId) already exists, the insert operation
     * will be ignored. This prevents duplicates.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToWishlist(item: WishlistItem)

    /**
     * Deletes an item from the wishlist.
     * @param item The WishlistItem object to be deleted, identified by its primary key.
     */
    @Delete
    suspend fun removeFromWishlist(item: WishlistItem)

    /**
     * Retrieves all items in a user's wishlist.
     * @param userId The ID of the user.
     * @return A Flow that emits the list of wishlist items, allowing the UI to update automatically.
     */
    @Query("SELECT * FROM wishlist_items WHERE user_id = :userId")
    fun getWishlistForUser(userId: Int): Flow<List<WishlistItem>>

    /**
     * Checks if a specific product is in a specific user's wishlist.
     * @param userId The ID of the user.
     * @param productId The ID of the product.
     * @return A Flow that emits the WishlistItem if it exists, or null otherwise.
     * This is useful for reactively updating UI elements, like a "favorite" icon.
     */
    @Query("SELECT * FROM wishlist_items WHERE user_id = :userId AND product_id = :productId")
    fun isProductInWishlist(userId: Int, productId: Int): Flow<WishlistItem?>
}