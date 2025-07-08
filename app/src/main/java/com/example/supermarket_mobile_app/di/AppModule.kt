// di/AppModule.kt
package com.example.supermarket_mobile_app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.supermarket_mobile_app.data.AppDatabase
import com.example.supermarket_mobile_app.data.Category
import com.example.supermarket_mobile_app.data.CategoryDao
import com.example.supermarket_mobile_app.data.Product
import com.example.supermarket_mobile_app.data.ProductDao
import com.example.supermarket_mobile_app.data.ShoppingListDao
import com.example.supermarket_mobile_app.data.ShoppingListItemDao
import com.example.supermarket_mobile_app.data.User
import com.example.supermarket_mobile_app.data.UserDao
import com.example.supermarket_mobile_app.data.WishlistDao
import com.example.supermarket_mobile_app.repositories.CartRepository
import com.example.supermarket_mobile_app.repositories.HomeRepository
import com.example.supermarket_mobile_app.repositories.LoginRepository
import com.example.supermarket_mobile_app.repositories.WishlistRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Το Hilt Module είναι ένα αντικείμενο που "διδάσκει" το Hilt πώς να δημιουργεί εξαρτήσεις (dependencies).
 * @Module: Δηλώνει ότι αυτό το object είναι ένα Hilt Module.
 * @InstallIn(SingletonComponent::class): Ορίζει ότι οι εξαρτήσεις που παρέχονται εδώ
 * θα έχουν κύκλο ζωής ίδιο με την εφαρμογή (θα είναι Singletons).
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Παρέχει το μοναδικό instance της AppDatabase.
     * Εδώ ενσωματώνεται και η λογική για την αρχικοποίηση της βάσης με δοκιμαστικά δεδομένα.
     * @param context Το application context, που παρέχεται αυτόματα από το Hilt.
     * @param userDaoProvider Ένας Provider για το UserDao. Χρησιμοποιούμε Provider για να αποφύγουμε
     * κυκλικές εξαρτήσεις κατά τη δημιουργία του callback.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        userDaoProvider: Provider<UserDao>,
        categoryDaoProvider: Provider<CategoryDao>,
        productDaoProvider: Provider<ProductDao>
    ): AppDatabase {
        // Δημιουργούμε ένα ανώνυμο αντικείμενο RoomDatabase.Callback.
        val databaseCallback = object : RoomDatabase.Callback() {
            // Η μέθοδος onCreate() καλείται μόνο μία φορά, όταν το αρχείο της βάσης
            // δημιουργείται για πρώτη φορά στη συσκευή.
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Εκκινούμε μια coroutine σε ένα background thread (Dispatchers.IO)
                // για να εκτελέσουμε τις λειτουργίες της βάσης δεδομένων.
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(
                        userDaoProvider.get(),
                        categoryDaoProvider.get(),
                        productDaoProvider.get()
                    )
                }
            }
        }

        // Χτίζουμε το instance της βάσης δεδομένων.
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "supermarket_app_db"
        )
            .addCallback(databaseCallback) // Προσθέτουμε το callback για την αρχικοποίηση.
            .build()
    }

    /**
     * Μια private συνάρτηση που περιέχει τα δοκιμαστικά δεδομένα (seed data)
     * για την αρχικοποίηση της βάσης.
     */
    private suspend fun populateDatabase(
        userDao: UserDao,
        categoryDao: CategoryDao,
        productDao: ProductDao
    ) {
        // 1. Εισαγωγή του δοκιμαστικού χρήστη
        userDao.insertUser(User(id=1, username = "user123", password = "password123"))

        // 2. Εισαγωγή Κατηγοριών
        val cat1 = Category(id = 1, name = "Γαλακτοκομικά & Αυγά")
        val cat2 = Category(id = 2, name = "Φρούτα & Λαχανικά")
        val cat3 = Category(id = 3, name = "Αναψυκτικά & Ποτά")
        val cat4 = Category(id = 4, name = "Κρεοπωλείο")
        val cat5 = Category(id = 5, name = "Είδη Αρτοποιίας")
        val cat6 = Category(id = 6, name = "Κατεψυγμένα Τρόφιμα")
        val cat7 = Category(id = 7, name = "Είδη Πρωινού")
        val cat8 = Category(id = 8, name = "Ζυμαρικά & Ρύζι")

        categoryDao.insertCategory(cat1)
        categoryDao.insertCategory(cat2)
        categoryDao.insertCategory(cat3)
        categoryDao.insertCategory(cat4)
        categoryDao.insertCategory(cat5)
        categoryDao.insertCategory(cat6)
        categoryDao.insertCategory(cat7)
        categoryDao.insertCategory(cat8)

        // 3. Εισαγωγή Προϊόντων
        // Γαλακτοκομικά & Αυγά (categoryId = 1)
        productDao.insertProduct(Product(id=1, name = "Γάλα Φρέσκο", description = "1.5L Πλήρες", imageUrl = "", price = 1.50, categoryId = 1))
        productDao.insertProduct(Product(id=2, name = "Γιαούρτι Στραγγιστό", description = "200g 2% Λιπαρά", imageUrl = "", price = 0.90, categoryId = 1, onOffer = true))
        productDao.insertProduct(Product(id=3, name = "Φέτα Βαρελίσια", description = "400g Π.Ο.Π.", imageUrl = "", price = 5.20, categoryId = 1))
        productDao.insertProduct(Product(id=4, name = "Αυγά Ελευθέρας Βοσκής", description = "6 τμχ.", imageUrl = "", price = 3.10, categoryId = 1))

        // Φρούτα & Λαχανικά (categoryId = 2)
        productDao.insertProduct(Product(id=5, name = "Μπανάνες", description = "Ανά κιλό", imageUrl = "", price = 1.80, categoryId = 2))
        productDao.insertProduct(Product(id=6, name = "Μήλα Κόκκινα", description = "Ανά κιλό", imageUrl = "", price = 2.10, categoryId = 2))
        productDao.insertProduct(Product(id=7, name = "Ντομάτες", description = "Ανά κιλό", imageUrl = "", price = 1.60, categoryId = 2, onOffer = true))
        productDao.insertProduct(Product(id=8, name = "Πατάτες", description = "Σακούλα 3kg", imageUrl = "", price = 2.50, categoryId = 2))
        productDao.insertProduct(Product(id=9, name = "Μαρούλι Iceberg", description = "Τεμάχιο", imageUrl = "", price = 0.80, categoryId = 2))

        // Αναψυκτικά & Ποτά (categoryId = 3)
        productDao.insertProduct(Product(id=10, name = "Coca-Cola", description = "500ml", imageUrl = "", price = 1.00, categoryId = 3))
        productDao.insertProduct(Product(id=11, name = "Χυμός Πορτοκάλι", description = "1L Φυσικός Χυμός", imageUrl = "", price = 2.20, categoryId = 3))
        productDao.insertProduct(Product(id=12, name = "Νερό Εμφιαλωμένο", description = "6 x 1.5L", imageUrl = "", price = 1.90, categoryId = 3, onOffer = true))

        // Κρεοπωλείο (categoryId = 4)
        productDao.insertProduct(Product(id=13, name = "Κοτόπουλο Στήθος", description = "Ανά κιλό", imageUrl = "", price = 8.50, categoryId = 4))
        productDao.insertProduct(Product(id=14, name = "Μοσχαρίσιος Κιμάς", description = "500g", imageUrl = "", price = 6.20, categoryId = 4))
        productDao.insertProduct(Product(id=15, name = "Χοιρινές Μπριζόλες", description = "Ανά κιλό", imageUrl = "", price = 7.10, categoryId = 4, onOffer = true))

        // Είδη Αρτοποιίας (categoryId = 5)
        productDao.insertProduct(Product(id=16, name = "Ψωμί Του Τοστ", description = "Σταρένιο 700g", imageUrl = "", price = 2.30, categoryId = 5))
        productDao.insertProduct(Product(id=17, name = "Κρουασάν Βουτύρου", description = "Τεμάχιο", imageUrl = "", price = 1.20, categoryId = 5))

        // Κατεψυγμένα Τρόφιμα (categoryId = 6)
        productDao.insertProduct(Product(id=18, name = "Πίτσα Μαργαρίτα", description = "Κατεψυγμένη 450g", imageUrl = "", price = 4.50, categoryId = 6, onOffer = true))
        productDao.insertProduct(Product(id=19, name = "Αρακάς", description = "Κατεψυγμένος 1kg", imageUrl = "", price = 3.30, categoryId = 6))

        // Είδη Πρωινού (categoryId = 7)
        productDao.insertProduct(Product(id=20, name = "Δημητριακά Ολικής Άλεσης", description = "375g", imageUrl = "", price = 3.80, categoryId = 7))
        productDao.insertProduct(Product(id=21, name = "Μέλι Ανθέων", description = "450g", imageUrl = "", price = 6.50, categoryId = 7))

        // Ζυμαρικά & Ρύζι (categoryId = 8)
        productDao.insertProduct(Product(id=22, name = "Σπαγγέτι No.6", description = "500g", imageUrl = "", price = 1.10, categoryId = 8))
        productDao.insertProduct(Product(id=23, name = "Ρύζι Καρολίνα", description = "1kg", imageUrl = "", price = 2.40, categoryId = 8, onOffer = true))
    }


    // --- DAO Providers ---
    // Οι παρακάτω συναρτήσεις λένε στο Hilt πώς να παρέχει το κάθε DAO,
    // απλά ζητώντας το από το instance της AppDatabase.
    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideShoppingListDao(db: AppDatabase) = db.shoppingListDao()

    @Provides
    @Singleton
    fun provideShoppingListItemDao(db: AppDatabase) = db.shoppingListItemDao()

    @Provides
    @Singleton
    fun provideWishlistDao(db: AppDatabase) = db.wishlistDao()


    // --- Repository Providers ---
    // Οι παρακάτω συναρτήσεις λένε στο Hilt πώς να παρέχει το κάθε Repository,
    // δίνοντάς του τα DAOs που χρειάζεται.
    @Provides
    @Singleton
    fun provideLoginRepository(userDao: UserDao) = LoginRepository(userDao)

    @Provides
    @Singleton
    fun provideHomeRepository(categoryDao: CategoryDao, productDao: ProductDao) = HomeRepository(categoryDao, productDao)

    @Provides
    @Singleton
    fun provideCartRepository(
        shoppingListDao: ShoppingListDao,
        shoppingListItemDao: ShoppingListItemDao
    ) = CartRepository(shoppingListDao, shoppingListItemDao)

    @Provides
    @Singleton
    fun provideWishlistRepository(wishlistDao: WishlistDao) = WishlistRepository(wishlistDao)
}