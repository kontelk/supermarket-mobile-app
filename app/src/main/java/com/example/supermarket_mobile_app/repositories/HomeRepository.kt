// repositories/HomeRepository.kt
package com.example.supermarket_mobile_app.repositories

import com.example.supermarket_mobile_app.data.CategoryDao
import com.example.supermarket_mobile_app.data.ProductDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository που διαχειρίζεται τις λειτουργίες δεδομένων για την κεντρική οθόνη,
 * όπως η ανάκτηση των λιστών προϊόντων και κατηγοριών.
 * Λειτουργεί ως ενδιάμεσος μεταξύ του ViewModel και των DAOs.
 * @Singleton: Αυτή η ετικέτα δηλώνει στο Hilt να δημιουργήσει μόνο ένα αντικείμενο (instance)
 * αυτού του repository για ολόκληρη την εφαρμογή.
 */
@Singleton
class HomeRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val productDao: ProductDao
) {
    /**
     * Ανακτά όλες τις κατηγορίες από τη βάση δεδομένων μέσω του CategoryDao.
     * @return Ένα Flow που εκπέμπει τη λίστα με όλες τις κατηγορίες.
     */
    fun getAllCategories() = categoryDao.getAllCategories()

    /**
     * Ανακτά όλα τα προϊόντα από τη βάση δεδομένων μέσω του ProductDao.
     * @return Ένα Flow που εκπέμπει τη λίστα με όλα τα προϊόντα.
     */
    fun getAllProducts() = productDao.getAllProducts()

    /**
     * Ανακτά τα προϊόντα που ανήκουν σε μια συγκεκριμένη κατηγορία.
     * @param categoryId Το ID της κατηγορίας.
     * @return Ένα Flow που εκπέμπει τη λίστα των φιλτραρισμένων προϊόντων.
     */
    fun getProductsByCategory(categoryId: Int) = productDao.getProductsByCategory(categoryId)

    /**
     * Ανακτά ένα μεμονωμένο προϊόν μέσω του μοναδικού του ID.
     * @param productId Το ID του προϊόντος.
     * @return Ένα Flow που εκπέμπει το προϊόν, ή null αν δεν βρεθεί.
     */
    fun getProductById(productId: Int) = productDao.getProductById(productId)
}