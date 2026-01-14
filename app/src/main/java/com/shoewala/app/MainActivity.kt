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
import com.shoewala.app.data.model.Product
//import com.shoewala.app.ui.product.ProductDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShoewalaTheme {

                // ✅ 1. Login state
                var isLoggedIn by remember {
                    mutableStateOf(FirebaseUtil.getAuth().currentUser != null)
                }

                // ✅ 2. Selected product state (THIS WAS MISSING)
                var selectedProduct by remember {
                    mutableStateOf<Product?>(null)
                }

                // 🔁 Listen to Firebase auth changes
                DisposableEffect(Unit) {
                    val listener = FirebaseAuth.AuthStateListener {
                        isLoggedIn = it.currentUser != null
                        if (!isLoggedIn) {
                            selectedProduct = null // reset on logout
                        }
                    }
                    FirebaseUtil.getAuth().addAuthStateListener(listener)

                    onDispose {
                        FirebaseUtil.getAuth().removeAuthStateListener(listener)
                    }
                }

                // 🧭 Simple navigation logic
                when {
                    !isLoggedIn -> {
                        AuthScreen(onAuthSuccess = {})
                    }

                    selectedProduct == null -> {
                        HomeScreen { product ->
                            selectedProduct = product
                        }
                    }

//                    else -> {
//                        ProductDetailScreen(
//                            product = selectedProduct!!,
//                            onBack = { selectedProduct = null }
//                        )
//                    }
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



