// ui/navigation/Screen.kt
package com.example.supermarket_mobile_app.ui.navigation

/**
 * Μια sealed class που αναπαριστά μια οθόνη στον
 * κεντρικό γράφο πλοήγησης της εφαρμογής.
 * Η χρήση sealed class μας επιτρέπει να ορίσουμε ένα περιορισμένο σύνολο από
 * πιθανές οθόνες, κάνοντας τον κώδικα πιο ασφαλή και ευανάγνωστο.
 *
 * @param route Η μοναδική συμβολοσειρά (path) που χρησιμοποιείται για την πλοήγηση.
 */
sealed class Screen(val route: String) {
    /**
     * Αντιπροσωπεύει την οθόνη Σύνδεσης (Login).
     */
    object Login : Screen("login_screen")

    /**
     * Αντιπροσωπεύει την κύρια οθόνη της εφαρμογής (αυτή που περιέχει την κάτω μπάρα πλοήγησης).
     */
    object Home : Screen("home_screen")

    /**
     * Αντιπροσωπεύει την οθόνη Λεπτομερειών Προϊόντος.
     * Η διαδρομή περιλαμβάνει ένα placeholder "{productId}" για το όρισμα που δέχεται.
     */
    object ProductDetails : Screen("product_details/{productId}") {
        /**
         * Μια βοηθητική συνάρτηση για την εύκολη κατασκευή της πλήρους διαδρομής μαζί με το ID του προϊόντος.
         * Για παράδειγμα, η κλήση withArgs("5") θα επιστρέψει "product_details/5".
         * @param productId Το ID του προϊόντος προς το οποίο θα γίνει η πλοήγηση.
         */
        fun withArgs(productId: String): String {
            return "product_details/$productId"
        }
    }
}