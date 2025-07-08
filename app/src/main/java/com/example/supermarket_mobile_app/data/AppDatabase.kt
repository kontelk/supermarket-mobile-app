// data/AppDatabase.kt
package com.example.supermarket_mobile_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.util.Date

/**
 * Η κεντρική κλάση της βάσης δεδομένων για ολόκληρη την εφαρμογή.
 * Αυτή η κλάση είναι αφηρημένη (abstract) και το Room αναλαμβάνει την υλοποίησή της.
 */
@Database(
    // Λίστα με όλες τις κλάσεις-οντότητες που θα μετατραπούν σε πίνακες στη βάση.
    entities = [
        User::class,
        Category::class,
        Product::class,
        ShoppingList::class,
        ShoppingListItem::class,
        WishlistItem::class
    ],
    version = 1, // Η έκδοση της βάσης. Πρέπει να αυξηθεί αν αλλάξει το σχήμα (π.χ. νέος πίνακας).
    exportSchema = false // Απενεργοποιεί την εξαγωγή του σχήματος της βάσης σε αρχείο JSON.
)
@TypeConverters(Converters::class) // Δηλώνει στο Room να χρησιμοποιεί τους μετατροπείς που ορίσαμε (π.χ. για Date <-> Long).
abstract class AppDatabase : RoomDatabase() {

    // Αφηρημένες συναρτήσεις που επιστρέφουν τα DAOs.
    // Το Room θα δημιουργήσει αυτόματα το σώμα αυτών των συναρτήσεων.
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingListItemDao(): ShoppingListItemDao
    abstract fun wishlistDao(): WishlistDao

    // Το companion object περιέχει τη λογική για τη δημιουργία ενός μοναδικού instance (Singleton) της βάσης.
    companion object {
        // Η ετικέτα @Volatile διασφαλίζει ότι η τιμή της μεταβλητής INSTANCE
        // είναι πάντα ενημερωμένη και ορατή σε όλα τα threads ταυτόχρονα.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Επιστρέφει το μοναδικό instance της βάσης δεδομένων.
         * Αν δεν υπάρχει, το δημιουργεί με ασφαλή τρόπο (thread-safe).
         * @param context Το application context.
         */
        fun getInstance(context: Context): AppDatabase {
            // Το synchronized μπλοκάρει την πρόσβαση από πολλαπλά threads ταυτόχρονα,
            // αποτρέποντας τη δημιουργία δύο instances της βάσης κατά λάθος.
            synchronized(this) {
                var instance = INSTANCE

                // Αν το instance δεν έχει αρχικοποιηθεί, το δημιουργούμε.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "supermarket_app_db" // Το όνομα του αρχείου της βάσης δεδομένων στη συσκευή.
                    )
                        .build() // Χτίζει το instance της βάσης.
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}