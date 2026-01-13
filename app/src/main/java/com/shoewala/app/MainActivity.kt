package com.shoewala.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseUser
import com.shoewala.app.ui.theme.ShoewalaTheme
import android.util.Log
import com.shoewala.app.ui.auth.AuthScreen
import com.shoewala.app.ui.home.HomeScreen
import com.shoewala.app.utils.FirebaseUtil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoewalaTheme {
                if (FirebaseUtil.getAuth().currentUser != null) {
                    HomeScreen()
                } else {
                    AuthScreen(
                        onAuthSuccess = { /* recomposition will happen */ }
                    )
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



