// data/ShoppingListItemDao.kt
package com.example.supermarket_mobile_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the ShoppingListItem entity.
 * Defines the database access methods for the 'shopping_list_items' table,
 * which acts as a link between shopping lists and products.
 */
@Dao
interface ShoppingListItemDao {

    /**
     * Inserts a new item into a shopping list.
     * @param onConflict = OnConflictStrategy.REPLACE: Since we have a composite primary key
     * (listId, productId), if we try to insert an item that already exists in the list,
     * this strategy will replace the old entry. This is useful for updating the quantity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingListItem)

    /**
     * Deletes an item from a shopping list.
     * @param item The ShoppingListItem object to be deleted. Room identifies it by its primary key.
     */
    @Delete
    suspend fun deleteItem(item: ShoppingListItem)

    /**
     * Retrieves all raw ShoppingListItem objects for a specific shopping list.
     * @param listId The ID of the shopping list.
     * @return A Flow that emits the list of items for the given list.
     */
    @Query("SELECT * FROM shopping_list_items WHERE list_id = :listId")
    fun getItemsForList(listId: Int): Flow<List<ShoppingListItem>>

    /**
     * Retrieves detailed information for each item in a shopping list by joining
     * the 'shopping_list_items' table with the 'products' table.
     * This query is crucial for the UI as it gets all necessary display data in one go.
     * @param listId The ID of the shopping list.
     * @return A Flow that emits a list of CartItemDetails objects, ready for display.
     */
    @Query("""
        SELECT p.id as productId, p.name, p.price, sli.quantity
        FROM shopping_list_items as sli
        INNER JOIN products as p ON sli.product_id = p.id
        WHERE sli.list_id = :listId
    """)
    fun getCartItemDetailsForList(listId: Int): Flow<List<CartItemDetails>>
}