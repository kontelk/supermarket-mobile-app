// ui/theme/Theme.kt
package com.example.supermarket_mobile_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Ορίζουμε την παλέτα χρωμάτων για το σκοτεινό θέμα (dark theme).
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Ορίζουμε την παλέτα χρωμάτων για το φωτεινό θέμα (light theme).
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Άλλες προεπιλεγμένες τιμές χρωμάτων μπορούν να παρακαμφθούν εδώ
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

/**
 * Η κεντρική Composable συνάρτηση του θέματος της εφαρμογής.
 * Οποιοδήποτε Composable τοποθετηθεί μέσα σε αυτήν, θα κληρονομήσει τις ιδιότητες του θέματος
 * (χρώματα, τυπογραφία, σχήματα).
 *
 * @param darkTheme Αν θα πρέπει να χρησιμοποιηθεί το σκοτεινό θέμα. Από προεπιλογή,
 * ακολουθεί τη ρύθμιση του συστήματος (isSystemInDarkTheme()).
 * @param dynamicColor Ενεργοποιεί το "δυναμικό χρώμα" (διαθέσιμο σε Android 12+).
 * Αν είναι true, η εφαρμογή θα αντλήσει τα χρώματά της από την ταπετσαρία του χρήστη.
 * @param content Το περιεχόμενο του UI της εφαρμογής που θα σχεδιαστεί μέσα στο θέμα.
 */
@Composable
fun SupermarketmobileappTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Επιλογή της σωστής παλέτας χρωμάτων.
    val colorScheme = when {
        // Αν το δυναμικό χρώμα υποστηρίζεται και είναι ενεργοποιημένο...
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            // ...χρησιμοποιούμε τη δυναμική παλέτα (είτε σκοτεινή είτε φωτεινή).
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Αλλιώς, χρησιμοποιούμε τη στατική παλέτα που ορίσαμε παραπάνω.
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Το LocalView.current μας δίνει πρόσβαση στο View της τρέχουσας οθόνης.
    val view = LocalView.current
    if (!view.isInEditMode) {
        // Το SideEffect εκτελεί κώδικα μετά από κάθε επιτυχημένο recomposition.
        // Εδώ το χρησιμοποιούμε για να αλλάξουμε το χρώμα της status bar του συστήματος.
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Το MaterialTheme είναι το Composable που εφαρμόζει το θέμα στα παιδιά του.
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Η τυπογραφία ορίζεται στο αρχείο ui/theme/Type.kt
        content = content
    )
}