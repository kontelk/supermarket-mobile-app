// data/Converters.kt
package com.example.supermarket_mobile_app.data

import androidx.room.TypeConverter
import java.util.Date

/**
 * Αυτή η κλάση περιέχει μετατροπείς τύπων (Type Converters) για το Room.
 * Οι μετατροπείς αυτοί επιτρέπουν στο Room να αποθηκεύει και να ανακτά τύπους δεδομένων
 * που δεν υποστηρίζει από προεπιλογή, όπως το java.util.Date.
 */
class Converters {
    /**
     * Μετατρέπει ένα Timestamp (τύπου Long) από τη βάση δεδομένων σε ένα αντικείμενο Date.
     * Το Room χρησιμοποιεί αυτή τη συνάρτηση όταν διαβάζει δεδομένα από τη βάση.
     * @param value Η τιμή Long (timestamp) από τη βάση. Μπορεί να είναι null.
     * @return Ένα αντικείμενο Date, ή null αν η τιμή εισόδου ήταν null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        // Αν η τιμή 'value' δεν είναι null, δημιουργεί ένα νέο Date αντικείμενο.
        // Αλλιώς, επιστρέφει null.
        return value?.let { Date(it) }
    }

    /**
     * Μετατρέπει ένα αντικείμενο Date από την εφαρμογή σε ένα Timestamp (τύπου Long)
     * για αποθήκευση στη βάση δεδομένων.
     * Το Room χρησιμοποιεί αυτή τη συνάρτηση όταν γράφει δεδομένα στη βάση.
     * @param date Το αντικείμενο Date. Μπορεί να είναι null.
     * @return Η τιμή Long (timestamp), ή null αν το Date ήταν null.
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        // Επιστρέφει τον αριθμό των χιλιοστων του δευτερολέπτου από την εποχή (epoch)
        // του αντικειμένου Date, ή null αν το ίδιο το Date είναι null.
        return date?.time
    }
}