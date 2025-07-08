// repositories/CartRepository.kt
package com.example.supermarket_mobile_app.repositories

import com.example.supermarket_mobile_app.data.CartItemDetails
import com.example.supermarket_mobile_app.data.ShoppingList
import com.example.supermarket_mobile_app.data.ShoppingListDao
import com.example.supermarket_mobile_app.data.ShoppingListItem
import com.example.supermarket_mobile_app.data.ShoppingListItemDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository που διαχειρίζεται όλες τις λειτουργίες που σχετίζονται με το καλάθι αγορών.
 * Λειτουργεί ως ενδιάμεσος μεταξύ των ViewModels και των DAOs του καλαθιού.
 * @Singleton: Η ετικέτα αυτή δηλώνει στο Hilt να δημιουργήσει μόνο ένα αντικείμενο (instance)
 * αυτού του repository για ολόκληρη την εφαρμογή.
 */
@Singleton
class CartRepository @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingListItemDao: ShoppingListItemDao
) {
    /**
     * Προσθέτει ένα προϊόν στο ενεργό καλάθι του χρήστη.
     * @param userId Το ID του χρήστη.
     * @param productId Το ID του προϊόντος προς προσθήκη.
     */
    suspend fun addProductToCart(userId: Int, productId: Int) {
        // 1. Βρίσκουμε την ενεργή λίστα αγορών του χρήστη.
        // Το firstOrNull() παίρνει την πρώτη τιμή από το Flow ή null αν το Flow είναι άδειο.
        var activeList = shoppingListDao.getActiveShoppingListForUser(userId).firstOrNull()

        // Αν δεν υπάρχει ενεργή λίστα, δημιουργούμε μια καινούργια.
        if (activeList == null) {
            val newList = ShoppingList(userId = userId, creationDate = Date(), status = "active")
            shoppingListDao.insertShoppingList(newList)
            // Αφού τη δημιουργήσαμε, τη ζητάμε ξανά για να πάρουμε το ID της.
            activeList = shoppingListDao.getActiveShoppingListForUser(userId).firstOrNull()
        }

        // 2. Προσθέτουμε το προϊόν στην ενεργή λίστα.
        activeList?.let { list ->
            // Αν το προϊόν υπάρχει ήδη, αύξησε την ποσότητα αντί να το ξαναπροσθέτεις.
            val item = ShoppingListItem(listId = list.id, productId = productId, quantity = 1)
            shoppingListItemDao.insertItem(item)
        }
    }

    /**
     * Χρησιμοποιεί flatMapLatest για να "αλλάξει" από το Flow της λίστας
     * στο Flow των αντικειμένων του καλαθιού.
     * @param userId Το ID του χρήστη.
     * @return Ένα Flow που εκπέμπει τη λίστα με τα αντικείμενα του καλαθιού.
     */
    fun getCartItems(userId: Int): Flow<List<CartItemDetails>> {
        return shoppingListDao.getActiveShoppingListForUser(userId)
            .flatMapLatest { activeList ->
                if (activeList != null) {
                    // Αν βρεθεί ενεργή λίστα, επιστρέφουμε το Flow με τα αντικείμενά της.
                    shoppingListItemDao.getCartItemDetailsForList(activeList.id)
                } else {
                    // Αν δεν υπάρχει ενεργή λίστα, επιστρέφουμε ένα Flow με άδεια λίστα.
                    flowOf(emptyList())
                }
            }
    }
}