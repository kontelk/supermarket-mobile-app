// SupermarketApp.kt
package com.example.supermarket_mobile_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Η ετικέτα @HiltAndroidApp μετατρέπει αυτή την κλάση στο σημείο εισόδου (entry point)
 * για το Hilt. Ενεργοποιεί τη δημιουργία του κώδικα για το dependency injection
 * και δημιουργεί το application-level dependency container από το οποίο θα τροφοδοτούνται
 * όλα τα άλλα μέρη της εφαρμογής (Activities, ViewModels, κ.λπ.).
 *
 * Είναι απαραίτητο να δηλωθεί και στο αρχείο AndroidManifest.xml.
 */
@HiltAndroidApp
class SupermarketApp : Application() {
    /**
     * Η κλάση Application είναι το πρώτο πράγμα που δημιουργείται όταν ξεκινά η εφαρμογή.
     * Το σώμα της κλάσης μένει συνήθως κενό, καθώς όλη η αρχικοποίηση του Hilt
     * γίνεται αυτόματα μέσω της παραπάνω ετικέτας.
     */
}