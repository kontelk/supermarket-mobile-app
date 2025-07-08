// ui/theme/Type.kt
package com.example.supermarket_mobile_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Ορίζει το σύνολο των τυπογραφικών στυλ του Material Design που θα χρησιμοποιηθούν στην εφαρμογή.
// Μπορείτε να προσαρμόσετε κάθε στυλ ξεχωριστά.
val Typography = Typography(
    // Στυλ για το κυρίως σώμα κειμένου (μεγάλο μέγεθος).
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Στυλ για μεγάλους τίτλους.
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Στυλ για μικρές ετικέτες (π.χ., κάτω από εικονίδια).
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    /*
    Μπορείτε να ορίσετε και άλλα στυλ εδώ, αντικαθιστώντας τα προεπιλεγμένα:
    titleMedium = TextStyle(...),
    bodyMedium = TextStyle(...),
    headlineLarge = TextStyle(...),
    κ.λπ.
    */
)