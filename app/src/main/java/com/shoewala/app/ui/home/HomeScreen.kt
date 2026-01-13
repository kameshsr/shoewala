package com.shoewala.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shoewala.app.data.model.Product
import com.shoewala.app.data.repository.ProductRepository
import com.shoewala.app.utils.FirebaseUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        ProductRepository.getProducts(
            onSuccess = { products = it },
            onError = { }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shoewala") },
                actions = {
                    TextButton(onClick = {
                        FirebaseUtil.getAuth().signOut()
                    }) {
                        Text("Logout")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(products) { product ->
                Card(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(product.name, style = MaterialTheme.typography.titleMedium)
                        Text("₹${product.price}")
                    }
                }
            }
        }
    }
}
