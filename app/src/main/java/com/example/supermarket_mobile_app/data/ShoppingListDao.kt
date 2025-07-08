// data/ShoppingListDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the ShoppingList entity.
 * Defines the database access methods for the 'shopping_lists' table.
 */
@Dao
interface ShoppingListDao {

    /**
     * Inserts a new shopping list into the table.
     * @param onConflict = OnConflictStrategy.REPLACE: If a list with the same
     * ID already exists, the old entry will be replaced with the new one.
     * The function is a suspend function to be called from a coroutine scope.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(list: ShoppingList)

    /**
     * Finds the active shopping list for a specific user.
     * We assume that a user can only have one list with the status "active" at any given time.
     * @param userId The ID of the user whose active list we want to find.
     * @return A Flow that emits the active ShoppingList, or null if no active list is found.
     * The use of Flow ensures that the UI will be notified if the active list changes.
     */
    @Query("SELECT * FROM shopping_lists WHERE user_id = :userId AND status = 'active'")
    fun getActiveShoppingListForUser(userId: Int): Flow<ShoppingList?>
}