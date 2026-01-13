package com.shoewala.app.data.repository

import android.util.Log
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

                    val name = doc.getString("name") ?: ""
                    val price = doc.getDouble("price") ?: 0.0
                    val imageUrl = doc.getString("imageUrl") ?: ""

                    Log.d("IMG_URL", "Fetched imageUrl = $imageUrl")

                    Product(
                        id = doc.id,
                        name = name,
                        price = price,
                        imageUrl = imageUrl
                    )
                }
                onSuccess(list)
            }

    }
}
