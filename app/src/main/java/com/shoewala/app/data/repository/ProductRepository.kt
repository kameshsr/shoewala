package com.shoewala.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.shoewala.app.data.model.Product

object ProductRepository {

    private val db = FirebaseFirestore.getInstance()

    fun getProducts(
        onSuccess: (List<Product>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("products")
            .get()
            .addOnSuccessListener { result ->
                val list = result.documents.map { doc ->
                    Product(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        price = doc.getDouble("price") ?: 0.0,
                        imageUrl = doc.getString("imageUrl") ?: ""
                    )
                }
                onSuccess(list)
            }
            .addOnFailureListener { onError(it) }
    }
}
