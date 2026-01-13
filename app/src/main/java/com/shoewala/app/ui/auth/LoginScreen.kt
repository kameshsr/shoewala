package com.shoewala.app.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.shoewala.app.data.repository.UserRepository
import com.shoewala.app.utils.FirebaseUtil

@Composable
fun LoginScreen(
    onSwitchToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.length < 6) {
                    error = "Invalid email or password"
                    return@Button
                }

                FirebaseUtil.getAuth()
                    .signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        UserRepository.saveUser()
                        onLoginSuccess()
                    }
                    .addOnFailureListener {
                        error = it.message
                        Log.e("LOGIN", it.message ?: "Login failed")
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        TextButton(onClick = onSwitchToRegister) {
            Text("No account? Register")
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}
