package com.shoewala.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.shoewala.app.ui.auth.AuthScreen
import com.shoewala.app.ui.home.HomeScreen
import com.shoewala.app.ui.theme.ShoewalaTheme
import com.shoewala.app.utils.FirebaseUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoewalaTheme {
                var isLoggedIn by remember {
                    mutableStateOf(FirebaseUtil.getAuth().currentUser != null)
                }

                DisposableEffect(Unit) {
                    val listener = FirebaseAuth.AuthStateListener {
                        isLoggedIn = it.currentUser != null
                    }
                    FirebaseUtil.getAuth().addAuthStateListener(listener)

                    onDispose {
                        FirebaseUtil.getAuth().removeAuthStateListener(listener)
                    }
                }

                if (isLoggedIn) {
                    HomeScreen()
                } else {
                    AuthScreen(onAuthSuccess = {})
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoewalaTheme {
        Greeting("Android")
    }
}



