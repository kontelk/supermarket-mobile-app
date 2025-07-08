// ui/login/LoginScreen.kt
package com.example.supermarket_mobile_app.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supermarket_mobile_app.ui.theme.SupermarketmobileappTheme

/**
 * Η Composable συνάρτηση που σχεδιάζει την οθόνη Σύνδεσης.
 * Αυτή η συνάρτηση είναι "χαζή" (dumb component): απλώς εμφανίζει την κατάσταση που λαμβάνει
 * και στέλνει τα συμβάντα (events) του χρήστη προς το ViewModel.
 * @param uiState Το αντικείμενο κατάστασης που περιέχει όλες τις πληροφορίες για την εμφάνιση.
 * @param onEvent Μια συνάρτηση (lambda) που καλείται για να σταλεί ένα συμβάν στο ViewModel.
 */
@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit
) {
    // Μια στήλη που πιάνει όλη την οθόνη και κεντράρει τα περιεχόμενά της.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Κεντράρισμα στον κάθετο άξονα
        horizontalAlignment = Alignment.CenterHorizontally // Κεντράρισμα στον οριζόντιο άξονα
    ) {
        // Τίτλος της οθόνης
        Text(text = "Σύνδεση", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Πεδίο εισαγωγής για το όνομα χρήστη
        OutlinedTextField(
            value = uiState.username, // Η τιμή του πεδίου συνδέεται με το state
            onValueChange = { onEvent(LoginEvent.UsernameChanged(it)) }, // Στέλνει event όταν η τιμή αλλάζει
            label = { Text("Όνομα Χρήστη") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Πεδίο εισαγωγής για τον κωδικό πρόσβασης
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
            label = { Text("Κωδικός Πρόσβασης") },
            singleLine = true,
            // Κρύβει τους χαρακτήρες του κωδικού
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Κουμπί για την υποβολή της φόρμας σύνδεσης
        Button(
            onClick = { onEvent(LoginEvent.LoginButtonPressed) },
            // Το κουμπί είναι απενεργοποιημένο όσο η εφαρμογή επεξεργάζεται τη σύνδεση
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Είσοδος")
        }

        // Εμφανίζει μια ένδειξη φόρτωσης (loading indicator) μόνο όταν το isLoading είναι true
        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        // Εμφανίζει ένα μήνυμα σφάλματος μόνο αν υπάρχει κάποιο σφάλμα στο state
        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = uiState.error,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

/**
 * Μια συνάρτηση προεπισκόπησης (Preview) για το Android Studio.
 * Μας επιτρέπει να βλέπουμε το UI χωρίς να χρειάζεται να εκτελέσουμε ολόκληρη την εφαρμογή.
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SupermarketmobileappTheme {
        LoginScreen(
            uiState = LoginUiState(error = "Αυτό είναι ένα σφάλμα."),
            onEvent = {}
        )
    }
}