// repositories/WishlistRepository.kt
package com.example.supermarket_mobile_app.repositories

import com.example.supermarket_mobile_app.data.WishlistDao
import com.example.supermarket_mobile_app.data.WishlistItem
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository που διαχειρίζεται τις λειτουργίες της λίστας επιθυμιών.
 * @Singleton: Δηλώνει στο Hilt να δημιουργήσει μόνο ένα αντικείμενο (instance)
 * αυτού του repository για ολόκληρη την εφαρμογή.
 */
@Singleton
class WishlistRepository @Inject constructor(private val wishlistDao: WishlistDao) {

    /**
     * Ελέγχει αν ένα συγκεκριμένο προϊόν βρίσκεται στη λίστα επιθυμιών ενός χρήστη.
     * @param userId Το ID του χρήστη.
     * @param productId Το ID του προϊόντος.
     * @return Ένα Flow που εκπέμπει το WishlistItem αν υπάρχει, αλλιώς null.
     */
    fun isProductInWishlist(userId: Int, productId: Int) =
        wishlistDao.isProductInWishlist(userId, productId)

    /**
     * Προσθέτει ένα προϊόν στη λίστα επιθυμιών.
     * @param userId Το ID του χρήστη.
     * @param productId Το ID του προϊόντος.
     */
    suspend fun addToWishlist(userId: Int, productId: Int) {
        wishlistDao.insertToWishlist(WishlistItem(userId = userId, productId = productId))
    }

    /**
     * Αφαιρεί ένα προϊόν από τη λίστα επιθυμιών.
     * @param userId Το ID του χρήστη.
     * @param productId Το ID του προϊόντος.
     */
    suspend fun removeFromWishlist(userId: Int, productId: Int) {
        wishlistDao.removeFromWishlist(WishlistItem(userId = userId, productId = productId))
    }
}