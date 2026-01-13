package com.shoewala.app.ui.auth

import androidx.compose.runtime.*

@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }

    if (isLogin) {
        LoginScreen(
            onSwitchToRegister = { isLogin = false },
            onLoginSuccess = onAuthSuccess
        )
    } else {
        RegisterScreen(
            onSwitchToLogin = { isLogin = true },
            onRegisterSuccess = onAuthSuccess
        )
    }
}
